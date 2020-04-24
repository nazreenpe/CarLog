package com.nasreen.carlog.model;

import java.util.UUID;

public class Car {
    private String make;
    private String model;
    private Integer year;
    private String trim;
    private UUID id;

    public Car() {
    }

    public Car(String make, String model, Integer year, String trim) {
        this.id = UUID.randomUUID();
        this.make = make;
        this.model = model;
        this.year = year;
        this.trim = trim;
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
}
