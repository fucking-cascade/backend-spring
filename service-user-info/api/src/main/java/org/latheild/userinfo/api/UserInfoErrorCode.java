package org.latheild.userinfo.api;

import org.latheild.apiutils.api.ErrorCode;

public enum UserInfoErrorCode implements ErrorCode {
    UserInfoNotExist(400, "User info does not exist"),
    UserInfoExist(409, "User info already exists");

    private int status;
    private String message;

    UserInfoErrorCode(int status, String message) {
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
