package com.nasreen.carlog.model;

import java.util.UUID;

public class Car {
    private String make;
    private String model;
    private Integer year;
    private String trim;
    private UUID id;
    private UUID userId;

    public Car() {
    }

    public Car(String make, String model, Integer year, String trim, UUID userId) {
        this.userId = userId;
        this.id = UUID.randomUUID();
        this.make = make;
        this.model = model;
        this.year = year;
        this.trim = trim;
    }

    public Car(UUID id, String make, String model, int year, String trim, UUID userId) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.trim = trim;
        this.userId = userId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getTrim() {
        return trim;
    }

    public UUID getId() {
        return id;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public UUID getUserId() {
        return userId;
    }
}
