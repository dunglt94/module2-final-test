package controller;

import model.Record;
import model.NormalRecord;
import model.VIPRecord;
import storage.RecordStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordManager {
    static RecordStorage recordStorage = RecordStorage.getInstance();
    public static List<Record> records = recordStorage.readRecord();

    public void add() {
        System.out.println("1. Bệnh nhân thường");
        System.out.println("2. Bệnh nhân VIP");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Chọn loại bênh nhân: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Record record = null;

        switch (choice) {
            case 1:
                record = new NormalRecord();
                break;
            case 2:
                record = new VIPRecord();
                break;
            default:
                System.out.println("Hãy nhập số tương ứng với loại bệnh nhân");
        }

        if (record != null) {
            boolean recordCodeMatch = false;
            while (!recordCodeMatch) {
                System.out.print("Nhập mã bệnh án (BA-XXX): ");
                String recordCode = scanner.nextLine();

                Pattern recordCodePattern = Pattern.compile("^BA-[0-9]{3}$");
                Matcher matcher = recordCodePattern.matcher(recordCode);

                if (matcher.matches()) {
                    boolean codeExists = false;

                    for (Record record2 : records) {
                        if (record2.getRecordCode().equals(recordCode)) {
                            codeExists = true;
                            break;
                        }
                    }

                    if (codeExists) {
                        System.out.println("Bệnh án đã tồn tại!");
                    } else {
                        recordCodeMatch = true;
                        record.setRecordCode(recordCode);
                    }
                } else {
                    System.out.println("Mã bệnh án chưa đúng mẫu. " +
                            "Phải nhập đúng định dạng BA-XXX với XXX là các kí tự số");
                }
            }

            boolean patientCodeMatch = false;
            while (!patientCodeMatch) {
                System.out.print("Nhập mã bệnh nhân: ");
                String patientCode = scanner.nextLine();

                Pattern patientCodePattern = Pattern.compile("^BN-[0-9]{3}$");
                Matcher matcher = patientCodePattern.matcher(patientCode);

                if (matcher.matches()) {
                    patientCodeMatch = true;
                    record.setPatientCode(patientCode);
                } else {
                    System.out.println("Mã bệnh nhân chưa đúng mẫu. " +
                            "Phải nhập đúng định dạng BN-XXX với XXX là các kí tự số");
                }
            }


            System.out.print("Nhập tên bệnh nhân: ");
            String patientName = scanner.nextLine();
            record.setName(patientName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (true) {
                try {
                    System.out.print("Nhập ngày nhập viện (dd/MM/yyyy): ");
                    String dateString1 = scanner.nextLine();
                    LocalDate dateOfAdmission = LocalDate.parse(dateString1, formatter);
                    record.setDateOfAdmission(dateOfAdmission);
                    break; // Break the loop if parsing is successful
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }
            }

            boolean isValidDate = false;
            while (!isValidDate) {
                try {
                    System.out.print("Nhập ngày xuất viện (dd/MM/yyyy): ");
                    String dateString2 = scanner.nextLine();
                    LocalDate dischargeDate = LocalDate.parse(dateString2, formatter);

                    if (dischargeDate.isAfter(record.getDateOfAdmission())
                            || dischargeDate.isEqual(record.getDateOfAdmission())) {
                        record.setDischargeDate(dischargeDate);
                        isValidDate = true;
                    } else {
                        System.out.println("Ngày xuất viện không được là ngày trước ngày nhập viện.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }
            }


            System.out.print("Lý do nhập viện: ");
            String reason = scanner.nextLine();
            record.setReason(reason);

            if (record instanceof NormalRecord) {
                while (true) {
                    try{
                        System.out.print("Nhập phí nằm viện: ");
                        String feeInput = scanner.nextLine();
                        feeInput = feeInput.replace(".", "");
                        double fee = Double.parseDouble(feeInput);
                        ((NormalRecord) record).setFee(fee);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Định dạng số không hợp lệ!");
                    }
                }

            } else {
                System.out.println("Chọn loại VIP: ");
                System.out.println("1. VIP I");
                System.out.println("2. VIP II");
                System.out.println("3. VIP III");
                System.out.print("Chọn: ");
                int VIPChoice = scanner.nextInt();
                String vipType = "";
                switch (VIPChoice) {
                    case 1:
                        vipType = "VIP I";
                        break;
                    case 2:
                        vipType = "VIP II";
                        break;
                    case 3:
                        vipType = "VIP III";
                        break;
                    default:
                        System.out.println("Nhập số tương ứng với loại VIP");
                }
                ((VIPRecord) record).setVIPPackage(vipType);
            }
        }
        records.add(record);
        System.out.println("Đã thêm bệnh án vào file!");
        recordStorage.writeRecord(records);
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Nhập mã bệnh án muốn xoá (BA-XXX): ");
            String recordCode = scanner.nextLine();

            Pattern recordCodePattern = Pattern.compile("^BA-[0-9]{3}$");
            Matcher matcher = recordCodePattern.matcher(recordCode);

            if (matcher.matches()) {
                Record recordToRemove = null;
                for (Record record2 : records) {
                    if (record2.getRecordCode().equals(recordCode)) {
                        recordToRemove = record2;
                        break;
                    }
                }

                if (recordToRemove != null) {
                    while (true) {
                        System.out.println("Xác nhận xoá?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Chọn: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.println("Đã xoá bệnh án " + recordToRemove.getRecordCode());
                                for (Record record2 : records) {
                                    if (record2.getId() > recordToRemove.getId()) {
                                        int i = record2.getId();
                                        record2.setId(--i);
                                    }
                                }
                                records.remove(recordToRemove);
                                recordStorage.writeRecord(records);
                                showRecords();
                                break;
                            case 2:
                                System.out.println("Quay về menu chính");
                                break;
                            default:
                                System.out.println("Nhập số tương ứng với lựa chọn");
                        }

                        if (choice == 1 || choice == 2) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Không tìm thấy bênh án " + recordCode);
                }
                break;
            } else {
                System.out.println("Mã bệnh án chưa đúng mẫu. " +
                        "Phải nhập đúng định dạng BA-XXX với XXX là các kí tự số");
            }
        }

    }

    public void showRecords() {
        List<Record> records = recordStorage.readRecord();
        if (records.isEmpty()) {
            System.out.println("Không có bệnh án nào!");
        } else {
            for (Record record : records) {
                System.out.println(record);
            }
        }
    }
}
