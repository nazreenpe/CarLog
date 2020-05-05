package com.nasreen.carlog.service;

import com.nasreen.carlog.db.RecordRepository;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.Record;
import com.nasreen.carlog.request.RecordCreateRequest;
import com.nasreen.carlog.request.RecordUpdate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecordService {
    private RecordRepository repository;

    public RecordService(RecordRepository repository) {
        this.repository = repository;
    }

    public Record create(RecordCreateRequest request, Car car) {
        Record record = new Record(request.getDate(), car.getId());
        return repository.save(record);
    }

    public List<Record> list(Car car) {
        return repository.list(car.getId());
    }

    public Optional<Record> get(Car car, UUID id) {
        return repository.findById(car.getId(), id);
    }

    public Optional<Record> update(Car car, UUID id, RecordUpdate update) {
        return get(car, id)
                .flatMap(record -> {
                    update.getDate().ifPresent(record::setDate);
                    return repository.update(record);
                });
    }

    public void delete(Car car, UUID id) {
        repository.delete(car.getId(), id);
    }
}
