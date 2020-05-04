package com.nasreen.carlog.model;

import java.time.LocalDate;
import java.util.UUID;

public class MaintenanceRecord {
    private UUID id;
    private LocalDate date;
    private UUID carId;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(LocalDate date, UUID carId) {
        this.date = date;
        this.carId = carId;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }
}
