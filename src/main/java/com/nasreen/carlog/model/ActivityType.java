package com.nasreen.carlog.model;

public enum ActivityType {
    OIL_CHANGE("Oil Change"),
    TIRE_ROTATION("Tire rotation"),
    REPLACE_WIPER("Replace Wiper");

    private String name;

    ActivityType(String name) {
        this.name = name;
    }
}
