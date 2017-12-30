package org.latheild.userinfo.api;

import org.latheild.apiutils.api.ErrorCode;

public enum UserInfoErrorCode implements ErrorCode {
    UserInfoExist(409, "用户信息已存在");

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
