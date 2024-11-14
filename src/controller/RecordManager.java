package controller;

import model.BenhAn;
import model.BenhAnThuong;
import model.BenhAnVIP;
import storage.RecordStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordManager {
    static RecordStorage recordStorage = RecordStorage.getInstance();
    public static List<BenhAn> records = recordStorage.readRecord();

    public void add() {
        System.out.println("1. Bệnh nhân thường");
        System.out.println("2. Bệnh nhân VIP");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Chọn loại bênh nhân: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        BenhAn record = null;

        switch (choice) {
            case 1:
                record = new BenhAnThuong();
                break;
            case 2:
                record = new BenhAnVIP();
                break;
            default:
                System.out.println("Hãy nhập số tương ứng với loại bệnh nhân");
        }

        if (record != null) {

            boolean recordCodeMatch = false;
            String recordCode = "";
            while (!recordCodeMatch) {
                System.out.print("Nhập mã bệnh án (BA-XXX): ");
                recordCode = scanner.nextLine();

                Pattern recordCodePattern = Pattern.compile("^BA-[0-9]{3}$");
                Matcher matcher = recordCodePattern.matcher(recordCode);

                if (matcher.matches()) {
                    boolean codeExists = false;

                    for (BenhAn record2 : records) {
                        if (record2.getRecordCode().equals(recordCode)) {
                            codeExists = true;
                            break;
                        }
                    }

                    if (codeExists) {
                        System.out.println("Bệnh án đã tồn tại!");
                    }
                    else {
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
                Pattern patientCodePattern = Pattern.compile("^BN-[0-9]{3}$");
                Matcher matcher = patientCodePattern.matcher(recordCode);

                System.out.print("Nhập mã bệnh nhân: ");
                String patientCode = scanner.nextLine();

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
            System.out.print("Nhập ngày nhập viện (dd/MM/yyyy): ");
            String dateString1 = scanner.nextLine();
            LocalDate dateOfAdmission = LocalDate.parse(dateString1, formatter);
            record.setDateOfAdmission(dateOfAdmission);

            System.out.print("Nhập xuất nhập viện (dd/MM/yyyy): ");
            String dateString2 = scanner.nextLine();
            LocalDate dischargeDate = LocalDate.parse(dateString2, formatter);
            record.setDischargeDate(dischargeDate);

            System.out.println("Lý do nhập viện:");
            String reason = scanner.nextLine();
            record.setReason(reason);

            if (record instanceof BenhAnThuong) {
                System.out.print("Nhập phí nằm viện: ");
                double fee = scanner.nextDouble();
                scanner.nextLine();
                ((BenhAnThuong) record).setFee(fee);
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
                ((BenhAnVIP) record).setVIPPackage(vipType);
            }
        }
        records.add(record);
        System.out.println("Đã thêm bệnh án vào file!");
        recordStorage.writeRecord(records);
    }






    public void delete() {
        System.out.println("Nhập mã bệnh án muốn xoá");
        Scanner scanner = new Scanner(System.in);
        String recordCode = scanner.nextLine();
        for (BenhAn record : records) {
            if (record.getRecordCode().equals(recordCode)) {
                records.remove(record);
                break;
            }
        }
        recordStorage.writeRecord(records);
    }

    public void showRecords() {
        List<BenhAn> records = recordStorage.readRecord();
        if (records.isEmpty()) {
            System.out.println("Không có bệnh án nào!");
        } else {
            for (BenhAn record : records) {
                System.out.println(record);
            }
        }
    }
}
