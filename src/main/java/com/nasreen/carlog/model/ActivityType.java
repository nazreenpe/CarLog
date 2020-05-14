package com.nasreen.carlog.model;

public enum ActivityType {
    OIL_CHANGE("Oil Change"),
    TIRE_ROTATION("Tire Rotation"),
    REPLACE_WIPER("Replace Wiper"),
    REPLACE_AIR_FILTER("Replace air filter"),
    REPLACE_FUEL_FILTER("Replace fuel filter"),
    REPLACE_OIL_FILTER("Replace oil filter"),
    REPLACE_SPARK_PLUG("Replace spark plug"),
    REFILL_TRANSMISSION_FLUID("Refill transmission fluid"),
    CHECK_TRANSMISSION_FLUID("Check transmission fluid level"),
    REFILL_BRAKE_FLUID("Refill brake fluid"),
    CHECK_BRAKE_FLUID("Check brake fluid level"),
    REFILL_STEERING_FLUID("Refill steering fluid"),
    CHECK_STEERING_FLUID("Check steering fluid level"),
    REFILL_CLUTCH_FLUID("Refill clutch fluid"),
    CHECK_CLUTCH_FLUID("Check clutch fluid"),
    CHECK_BRAKE_PADS_LINERS("Check brake pads/liners"),
    CHECK_BRAKE_DISCS_DRUMS("Check brake discs/drums"),
    CHECK_BATTERY("Check battery"),
    CHECK_CHARGING_SYSTEMS("Check the charging systems"),
    CHECK_TIMING_BELT_CHAIN("Check the timing belt / chain"),
    REPLACE_TIMING_BELT_CHAIN("Replace the timing belt / chain"),
    CHECK_ERROR_CODES("Check error codes"),
    REPLACE_AC_FILTER("Replace AC filter"),
    OTHER("Other");

    private String publicName;

    public String getPublicName() {
        return publicName;
    }

    ActivityType(String name) {
        this.publicName = name;
    }
}
