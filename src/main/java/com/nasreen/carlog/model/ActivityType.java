package com.nasreen.carlog.model;

public enum ActivityType {
    OIL_CHANGE("Oil Change"),
    TIRE_ROTATION("Tire Rotation"),
    REPLACE_WIPER("Replace Wiper");

    private String publicName;

    public String getPublicName() {
        return publicName;
    }

    ActivityType(String name) {
        this.publicName = name;
    }
}
