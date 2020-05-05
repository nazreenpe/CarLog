package com.nasreen.carlog.request;

import java.time.LocalDate;
import java.util.UUID;

public class RecordCreateRequest {
    private LocalDate date;

    public RecordCreateRequest() {
    }

    public RecordCreateRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

}
