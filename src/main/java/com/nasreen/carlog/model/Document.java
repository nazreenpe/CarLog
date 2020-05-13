package com.nasreen.carlog.model;

import java.util.UUID;

public class Document {
    private String description;
    private UUID id;
    private UUID recordId;
    private String path;
    private String filename;

    public Document() {
    }

    public Document(String description, UUID recordId, String path, String filename) {
        this.description = description;
        this.recordId = recordId;
        this.path = path;
        this.filename = filename;
        this.id = UUID.randomUUID();
    }

    public Document(UUID id, UUID recordId, String description, String path, String filename) {
        this.description = description;
        this.id = id;
        this.recordId = recordId;
        this.path = path;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
