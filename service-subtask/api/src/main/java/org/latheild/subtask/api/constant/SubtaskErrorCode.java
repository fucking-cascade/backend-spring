package org.latheild.subtask.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum SubtaskErrorCode implements ErrorCode {
    SUBTASK_NOT_EXIST(404, "Subtask does not exist");

    private int status;
    private String message;

    SubtaskErrorCode(int status, String message) {
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
