package com.ilhamfidatama.event_api.model.response;

public enum Status {

    FAILED("failed"),
    SUCCESS("success");
    public final String value;

    Status(String value) {
        this.value = value;
    }
}
