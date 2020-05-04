package com.nasreen.carlog.request;

import com.nasreen.carlog.model.ActivityType;

public class ActivityCreate {
    private ActivityType type;

    public ActivityCreate() {
    }

    public ActivityCreate(ActivityType type) {
        this.type = type;
    }

    public ActivityType getType() {
        return type;
    }
}
