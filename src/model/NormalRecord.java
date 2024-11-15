package model;

import java.time.LocalDate;

public class NormalRecord extends Record {
    private double fee;

    public NormalRecord() {
        super();
    }

    public NormalRecord(double fee) {
        super();
        this.fee = fee;
    }

    public NormalRecord(String recordCode, String patientCode, String name,
                        LocalDate dateOfAdmission, LocalDate dischargeDate, String reason, double fee) {
        super(recordCode, patientCode, name, dateOfAdmission, dischargeDate, reason);
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
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
                ", phí nhập viện: " + this.getFee() + " VND";
    }
}
