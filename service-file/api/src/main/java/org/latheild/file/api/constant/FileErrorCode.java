package org.latheild.file.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum FileErrorCode implements ErrorCode {
    FILE_EXIST(409, "File already exists"),
    FILE_NOT_EXIST(404, "File does not exist");

    private int status;
    private String message;

    FileErrorCode(int status, String message) {
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
