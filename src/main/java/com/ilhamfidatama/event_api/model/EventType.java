package com.ilhamfidatama.event_api.model;

public enum EventType {

    ONLINE("online"),
    OFFLINE("offline");

    private final String type;
    EventType(String type) {this.type = type;}

    @Override
    public String toString() {
        return this.type;
    }
}
