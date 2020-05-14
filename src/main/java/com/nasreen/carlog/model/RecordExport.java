package com.nasreen.carlog.model;

import java.util.List;

public class RecordExport {
    private final Record record;
    private final List<Activity> activities;
    private final List<DocumentExport> documents;

    public RecordExport() {
        this(null, null, null);
    }

    public RecordExport(Record record, List<Activity> activities, List<DocumentExport> documents) {
        this.record = record;
        this.activities = activities;
        this.documents = documents;
    }

    public Record getRecord() {
        return record;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<DocumentExport> getDocuments() {
        return documents;
    }
}
