package com.nasreen.carlog.request;

import javax.validation.constraints.NotEmpty;

public class DocumentRequest {
    @NotEmpty
    private String description;
    @NotEmpty
    private String path;
    @NotEmpty
    private String filename;

    public DocumentRequest() {
    }

    public DocumentRequest(String description, String path, @NotEmpty String filename) {
        this.description = description;
        this.path = path;
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public String getFilename() {
        return filename;
    }
}
