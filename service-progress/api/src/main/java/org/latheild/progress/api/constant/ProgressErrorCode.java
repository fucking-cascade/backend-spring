package org.latheild.progress.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum ProgressErrorCode implements ErrorCode {
    ProgressNotExist(404, "Progress does not exist");

    private int status;
    private String message;

    ProgressErrorCode(int status, String message) {
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
