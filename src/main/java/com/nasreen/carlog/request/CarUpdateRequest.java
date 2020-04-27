package com.nasreen.carlog.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Optional;

public class CarUpdateRequest {
    private Optional<String> make;
    private Optional<String>  model;
    private Optional<Integer>  year;
    private Optional<String>  trim;

    public CarUpdateRequest() {
    }

    public Optional<String> getMake() {
        return make;
    }

    public Optional<String> getModel() {
        return model;
    }

    public Optional<Integer> getYear() {
        return year;
    }

    public Optional<String> getTrim() {
        return trim;
    }
}
