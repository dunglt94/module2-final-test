package controller;

public class DuplicateMedicalRecordException extends RuntimeException {
    public DuplicateMedicalRecordException(String message) {
        super("Bệnh án đã tồn tại!");
    }
}
