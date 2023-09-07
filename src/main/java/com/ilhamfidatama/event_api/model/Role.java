package com.ilhamfidatama.event_api.model;

public enum Role {

    CUSTOMER("CUSTOMER"),
    ORGANIZER("ORGANIZER"),
    ADMINISTRATOR("ADMINISTRATOR");

    private final String role;
    Role(String role) {this.role = role;}

    @Override
    public String toString() {
        return this.role;
    }
}