package com.nasreen.carlog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CarExport {
    private final User user;
    private final Car car;
    private final List<RecordExport> recordExports;

    public CarExport() {
        this(null, null, null);
    }

    public CarExport(User user, Car car, List<RecordExport> recordExports) {
        this.user = user;
        this.car = car;
        this.recordExports = recordExports;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    @JsonProperty("records")
    public List<RecordExport> getRecordExports() {
        return recordExports;
    }
}
