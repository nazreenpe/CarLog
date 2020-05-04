package com.nasreen.carlog.request;

import java.time.LocalDate;
import java.util.Optional;

public class MaintenanceRecordUpdate {
    private Optional<LocalDate> date;

    public MaintenanceRecordUpdate() {
        this.date = Optional.empty();
    }

    public MaintenanceRecordUpdate(Optional<LocalDate> date) {
        this.date = date;
    }

    public Optional<LocalDate> getDate() {
        return date;
    }
}
