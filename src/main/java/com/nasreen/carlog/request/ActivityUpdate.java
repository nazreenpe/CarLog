package com.nasreen.carlog.request;

import com.nasreen.carlog.model.ActivityType;

import java.util.Optional;

public class ActivityUpdate {
    private Optional<ActivityType> type = Optional.empty();

    public ActivityUpdate() {
    }

    public ActivityUpdate(Optional<ActivityType> type) {
        this.type = type;
    }

    public Optional<ActivityType> getType() {
        return type;
    }
}
