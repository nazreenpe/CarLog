package com.nasreen.carlog.model;

import java.net.URL;

public class DocumentExport {
    private final String filename;
    private final String description;
    private final URL url;

    public DocumentExport() {
        this(null, null, null);
    }

    public DocumentExport(String filename, String description, URL url) {
        this.filename = filename;
        this.description = description;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public String getDescription() {
        return description;
    }

    public URL getUrl() {
        return url;
    }
}
