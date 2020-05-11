package com.nasreen.carlog.request;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CarCreateRequest {
    @NotBlank
    private String make;
    @NotBlank
    private String model;
    @Min(1900)
    @NumberFormat
    private Integer year;
    @NotBlank
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
