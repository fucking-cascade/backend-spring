package org.latheild.user.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    EMAIL_EXIST(409, "Email already exists"),
    WRONG_PASSWORD(401,"Unauthorized, Wrong password"),
    USER_NOT_EXIST(404, "User does not exist");

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
