package com.nasreen.carlog.request;

public class CarCreateRequest {
    private String make;
    private String model;
    private Integer year;
    private String trim;

    public CarCreateRequest() {
    }

    public CarCreateRequest(String make, String model, Integer year, String trim) {
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

    public int getYear() {
        return year;
    }

    public String getTrim() {
        return trim;
    }
}
