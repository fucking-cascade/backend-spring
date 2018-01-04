package org.latheild.common.api;

public enum CommonTaskStatus implements TaskStatus {
    ONGOING(0, "ONGOING"),
    SUSPENDED(1, "SUSPENDED"),
    COMPLETE(2, "COMPLETE"),
    OVERDUE(3, "OVERDUE"),
    DEPRECATED(4, "DEPRECATED");

    private int status;

    private String message;

    CommonTaskStatus(int status, String message) {
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
