package com.nasreen.carlog.request;

import java.time.LocalDate;
import java.util.Optional;

public class RecordUpdate {
    private Optional<LocalDate> date;

    public RecordUpdate() {
        this.date = Optional.empty();
    }

    public RecordUpdate(Optional<LocalDate> date) {
        this.date = date;
    }

    public Optional<LocalDate> getDate() {
        return date;
    }
}
