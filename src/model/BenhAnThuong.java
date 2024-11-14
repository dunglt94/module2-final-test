package model;

import java.time.LocalDate;

public class BenhAnThuong extends BenhAn{
    private double fee;

    public BenhAnThuong() {
        super();
    }

    public BenhAnThuong(double fee) {
        super();
        this.fee = fee;
    }

    public BenhAnThuong(String recordCode, String patientCode, String name, double fee,
                        LocalDate dateOfAdmission, LocalDate dischargeDate, String reason) {
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
                ", mã bệnh án:" + super.getRecordCode() +
                ", mã bệnh nhân" + super.getPatientCode() +
                ", tên bệnh nhân: " + super.getName() +
                ", ngày nhập viện: " + super.getDateOfAdmission() +
                ", ngày xuất viện: " + super.getDischargeDate() +
                ", lý do nhập việm: " + super.getReason() +
                ", phí nhập viện" + this.getFee() + " VND";
    }
}
