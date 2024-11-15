package storage;

import model.Record;

import java.util.List;

public interface IRecordStorage {
    void writeRecord(List<Record> records);
    List<Record> readRecord();
}
