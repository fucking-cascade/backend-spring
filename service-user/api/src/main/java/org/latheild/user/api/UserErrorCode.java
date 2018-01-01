package org.latheild.user.api;

import org.latheild.apiutils.api.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    EmailExist(409, "Email already exists"),
    WrongPassword(401,"Wrong password"),
    UserNotExist(400, "User does not exist");

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
