package view;

import controller.RecordManager;

import java.util.Scanner;

public class Main {
    private static final RecordManager drinkManager = new RecordManager();
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
                    drinkManager.add();
                    break;
                case 2:
                    drinkManager.delete();
                    break;
                case 3:
                    drinkManager.showRecords();
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
