package org.latheild.user.api;

import org.latheild.apiutils.api.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    EmailExist(409, "邮箱已存在");

    private int status;
    private String message;

    UserErrorCode(int status, String message) {
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
