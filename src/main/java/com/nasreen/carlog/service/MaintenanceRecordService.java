package com.nasreen.carlog.service;

import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.MaintenanceRecord;
import com.nasreen.carlog.request.MaintenanceRecordCreateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaintenanceRecordService {
    List<MaintenanceRecord> records = new ArrayList<>();

    public MaintenanceRecord create(MaintenanceRecordCreateRequest request, Car car) {
        MaintenanceRecord record = new MaintenanceRecord(request.getDate(), car.getId());
        records.add(record);
        return record;
    }

    public List<MaintenanceRecord> list(Car car) {
        return records.stream()
        .filter(record -> record.getCarId().equals(car.getId()))
        .collect(Collectors.toList());
    }
}
