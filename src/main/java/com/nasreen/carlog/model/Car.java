package com.nasreen.carlog.model;

public class Car {
    private String make;
    private String model;
    private int year;
    private String trim;

    public Car() {
    }

    public Car(String make, String model, int year, String trim) {
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
