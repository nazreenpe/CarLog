package com.nasreen.carlog.request;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class DocumentRequest {
    @NotEmpty
    private String description;
    @NotEmpty
    private String path;

    public DocumentRequest() {
    }

    public DocumentRequest(String description, String path) {
        this.description = description;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }
}
