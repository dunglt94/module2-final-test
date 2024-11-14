package model;

import java.time.LocalDate;

public class BenhAnVIP extends BenhAn{
    private String VIPPackage;
    private int VIPDuration;

    public BenhAnVIP() {
        super();
    }

    public BenhAnVIP(String VIPPackage) {
        super();
        this.VIPPackage = VIPPackage;
    }

    public BenhAnVIP(String recordCode, String patientCode, String name, LocalDate dateOfAdmission,
                     LocalDate dischargeDate, String reason) {
        super(recordCode, patientCode, name, dateOfAdmission, dischargeDate, reason);
    }

    public BenhAnVIP(String recordCode, String patientCode, String name, LocalDate dateOfAdmission,
                     LocalDate dischargeDate, String reason, String VIPPackage) {
        super(recordCode, patientCode, name, dateOfAdmission, dischargeDate, reason);
        this.VIPPackage = VIPPackage;
    }

    public String getVIPPackage() {
        return VIPPackage;
    }

    public void setVIPPackage(String VIPPackage) {
        this.VIPPackage = VIPPackage;
    }

    public int getVIPDuration() {
        return VIPDuration;
    }

    public void setVIPDuration(int VIPDuration) {
        this.VIPDuration = VIPDuration;
    }

    @Override
    public String toString() {
        return "STT: " + super.getId() +
                ", mã bệnh án: " + super.getRecordCode() +
                ", mã bệnh nhân: " + super.getPatientCode() +
                ", tên bệnh nhân: " + super.getName() +
                ", ngày nhập viện: " + super.getDateOfAdmission() +
                ", ngày xuất viện: " + super.getDischargeDate() +
                ", lý do nhập việm: " + super.getReason() +
                ", loại VIP: " + this.getVIPPackage();
    }
}
