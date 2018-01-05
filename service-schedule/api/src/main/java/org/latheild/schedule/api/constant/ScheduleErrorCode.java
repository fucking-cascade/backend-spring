package org.latheild.schedule.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum ScheduleErrorCode implements ErrorCode {
    SCHEDULE_EXIST(409, "Schedule already exists"),
    SCHEDULE_NOT_EXIST(404, "Schedule does not exist");

    private int status;
    private String message;

    ScheduleErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
