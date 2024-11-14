package storage;

import model.BenhAn;
import model.BenhAnThuong;import model.BenhAnVIP;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RecordStorage implements IRecordStorage{
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String COMMA_DELIMITER = ",";
    private static final String DATA_RECORDS_CSV = "data/medical_records.csv";
    private static final String FILE_HEADER = "STT,mã bệnh án,name,mã bệnh nhân,tên bệnh nhân,ngày nhập viện," +
            "ngày xuất viện,lý do nhập việm,Loại bênh nhân";

    private RecordStorage() {}

    private static RecordStorage instance;
    public static RecordStorage getInstance() {
        if (instance == null) {
            instance = new RecordStorage();
        }
        return instance;
    }

    @Override
    public void writeRecord(List<BenhAn> records) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_RECORDS_CSV));

            writer.write(FILE_HEADER);
            writer.newLine();

            for (BenhAn record : records) {
                writer.write(String.valueOf(record.getId()));
                writer.write(COMMA_DELIMITER);
                writer.write(record.getRecordCode());
                writer.write(COMMA_DELIMITER);
                writer.write(record.getPatientCode());
                writer.write(COMMA_DELIMITER);
                writer.write(record.getName());
                writer.write(COMMA_DELIMITER);
                writer.write(record.getDateOfAdmission().format(DATE_FORMATTER));
                writer.write(COMMA_DELIMITER);
                writer.write(record.getDischargeDate().format(DATE_FORMATTER));
                writer.write(COMMA_DELIMITER);
                writer.write(record.getReason());
                if (record instanceof BenhAnThuong) {
                    writer.write(COMMA_DELIMITER);
                    writer.write(Double.toString(((BenhAnThuong) record).getFee()));
                } else if (record instanceof BenhAnVIP) {
                    writer.write(COMMA_DELIMITER);
                    writer.write(((BenhAnVIP) record).getVIPPackage());
                }

                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing CSV file !!!");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    @Override
    public List<BenhAn> readRecord() {
        List<BenhAn> records = new ArrayList<>();
        File file = new File(DATA_RECORDS_CSV);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                String[] column = line.split(COMMA_DELIMITER);
                if (counter == 1) continue;
                BenhAn record = getRecord(column);
                records.add(record);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error while reading CSV file !!!");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        return records;
    }

    private static BenhAn getRecord(String[] column) {
        int id = Integer.parseInt(column[0]);
        String recordCode = column[1];
        String patientCode = column[2];
        String name = column[3];
        LocalDate dateOfAdmission = null;
        LocalDate dischargeDate = null;

        try {
            dateOfAdmission = LocalDate.parse(column[4], DATE_FORMATTER);
            dischargeDate = LocalDate.parse(column[5], DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format for manufacturing date");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        String reason = column[6];
        String patientType = column[7];

        BenhAn record = null;

        String[] patientTypeArray = patientType.split(" ");
        if (patientTypeArray[0].equals("VIP")) {
            try {
                record = new BenhAnVIP(recordCode, patientCode, name, dateOfAdmission,
                        dischargeDate, reason, patientType);
                record.setId(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                record = new BenhAnThuong(recordCode, patientCode, name, dateOfAdmission,
                        dischargeDate, reason, Double.parseDouble(patientType));
                record.setId(id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return record;
    }
}
