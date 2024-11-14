package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class BenhAn {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static int counter = 0;
    private int id;
    private String recordCode;
    private String patientCode;
    private String name;
    private LocalDate dateOfAdmission;
    private LocalDate dischargeDate;
    private String reason;

    public BenhAn() {
        this.id = ++counter;
    }

    public BenhAn(String recordCode, String patientCode, String name, LocalDate dateOfAdmission, LocalDate dischargeDate, String reason) {
        this.id = ++counter;
        this.recordCode = recordCode;
        this.patientCode = patientCode;
        this.name = name;
        this.dateOfAdmission = dateOfAdmission;
        this.dischargeDate = dischargeDate;
        this.reason = reason;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        BenhAn.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "STT: " + id +
                ", Mã bệnh án: " + recordCode +
                ", mã bệnh nhân: " + patientCode +
                ", Tên bệnh nhân: " + name +
                ", ngày nhập viện: " + dateOfAdmission.format(DATE_FORMATTER) +
                ", ngày xuất viện: " + dischargeDate.format(DATE_FORMATTER) +
                ", lý do nhập việm: " + reason;
    }
}
