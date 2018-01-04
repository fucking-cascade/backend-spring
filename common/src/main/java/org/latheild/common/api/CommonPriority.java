package org.latheild.common.api;

public enum CommonPriority implements Priority {
    LOW_PRIORITY(0, "LOW PRIORITY"),
    MEDIUM_PRIORITY(1, "MEDIUM PRIORITY"),
    HIGH_PRIORITY(2, "HIGH PRIORITY");

    private int status;

    private String message;

    CommonPriority(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getStatusDescription() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
