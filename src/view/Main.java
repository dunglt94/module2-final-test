package view;

import controller.RecordManager;

import java.util.Scanner;

public class Main {
    private static final RecordManager recordManager = new RecordManager();
    public static void main(String[] args) {
        while (true) {
            System.out.println("--CHƯƠNG TRÌNH QUẢN LÝ BỆNH ÁN--");
            System.out.println("1. Thêm mới");
            System.out.println("2. Xoá");
            System.out.println("3. Xem danh sách các bệnh án");
            System.out.println("4. Thoát");

            System.out.print("Chọn chức năng: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    recordManager.add();
                    break;
                case 2:
                    recordManager.delete();
                    break;
                case 3:
                    recordManager.showRecords();
                    break;
                case 4:
                    System.out.println("Thoát chương trình!");
                    System.exit(0);
                default:
                    System.out.println("Hãy nhập số tương ứng với chức năng");
            }

        }
    }
}
