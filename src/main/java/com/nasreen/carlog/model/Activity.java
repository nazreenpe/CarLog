package com.nasreen.carlog.model;

import java.util.UUID;

public class Activity {
    private ActivityType type;
    private String notes;
    private UUID recordId;
    private UUID id;

    public Activity() {
    }

    public Activity(ActivityType type, String notes, UUID recordId) {
        this.type = type;
        this.notes = notes;
        this.recordId = recordId;
        this.id = UUID.randomUUID();
    }

    public Activity(UUID id, ActivityType type, String notes, UUID recordId) {
        this.id = id;
        this.type = type;
        this.notes = notes;
        this.recordId = recordId;
    }

    public ActivityType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getPublicName();
    }

    public UUID getRecordId() {
        return recordId;
    }

    public UUID getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
