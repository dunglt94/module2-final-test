package controller;

import model.BenhAn;
import model.BenhAnThuong;
import model.BenhAnVIP;
import storage.RecordStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

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
            System.out.print("Nhập mã bệnh án: ");
            String recordCode = scanner.nextLine();
            record.setRecordCode(recordCode);

            System.out.print("Nhập mã bệnh nhân: ");
            String patientCode = scanner.nextLine();
            record.setPatientCode(patientCode);

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
