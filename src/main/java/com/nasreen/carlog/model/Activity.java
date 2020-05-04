package com.nasreen.carlog.model;

import java.util.UUID;

public class Activity {
    private ActivityType type;
    private UUID recordId;
    private UUID id;

    public Activity() {
    }

    public Activity(ActivityType type, UUID recordId) {
        this.type = type;
        this.recordId = recordId;
        this.id = UUID.randomUUID();
    }

    public Activity(UUID id, ActivityType type, UUID recordId) {
        this.id = id;
        this.type = type;
        this.recordId = recordId;
    }

    public ActivityType getType() {
        return type;
    }

    public UUID getRecordId() {
        return recordId;
    }

    public UUID getId() {
        return id;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }
}
