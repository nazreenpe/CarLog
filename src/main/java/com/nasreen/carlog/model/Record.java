package com.nasreen.carlog.model;

import java.time.LocalDate;
import java.util.UUID;

public class Record {
    private UUID id;
    private LocalDate date;
    private UUID carId;

    public Record() {
    }

    public Record(LocalDate date, UUID carId) {
        this.date = date;
        this.carId = carId;
        this.id = UUID.randomUUID();
    }

    public Record(UUID id, LocalDate date, UUID carId) {
        this.id = id;
        this.date = date;
        this.carId = carId;
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
