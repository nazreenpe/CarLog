package com.nasreen.carlog.request;

import com.nasreen.carlog.model.ActivityType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ActivityCreate {
    @NotNull
    private ActivityType type;
    @NotEmpty
    private String notes;

    public ActivityCreate() {
    }

    public ActivityCreate(ActivityType type, String notes) {
        this.type = type;
        this.notes = notes;
    }

    public ActivityType getType() {
        return type;
    }

    public String getNotes() {
        return notes;
    }
}
