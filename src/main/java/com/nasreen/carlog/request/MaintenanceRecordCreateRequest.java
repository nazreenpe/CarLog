package com.nasreen.carlog.request;

import java.time.LocalDate;
import java.util.UUID;

public class MaintenanceRecordCreateRequest {
    private LocalDate date;

    public MaintenanceRecordCreateRequest() {
    }

    public MaintenanceRecordCreateRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

}
