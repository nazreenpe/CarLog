package com.nasreen.carlog.request;

import com.nasreen.carlog.model.ActivityType;

import java.util.Optional;

public class ActivityUpdate {
    private Optional<ActivityType> type = Optional.empty();
    private Optional<String> notes = Optional.empty();

    public ActivityUpdate() {
    }

    public ActivityUpdate(Optional<ActivityType> type, Optional<String> notes) {
        this.type = type;
        this.notes = notes;
    }

    public Optional<ActivityType> getType() {
        return type;
    }

    public Optional<String> getNotes() {
        return notes;
    }
}
