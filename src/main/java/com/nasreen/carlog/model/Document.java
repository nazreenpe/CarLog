package com.nasreen.carlog.model;

import java.util.UUID;

public class Document {
    private String description;
    private UUID id;
    private UUID recordId;
    private String path;

    public Document() {
    }

    public Document(String description, UUID recordId, String path) {
        this.description = description;
        this.recordId = recordId;
        this.path = path;
        this.id = UUID.randomUUID();
    }

    public Document(UUID id, UUID recordId, String description, String path) {
        this.description = description;
        this.id = id;
        this.recordId = recordId;
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UUID getId() {
        return id;
    }
}
