package org.latheild.task.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum TaskErrorCode implements ErrorCode {
    TaskNotExist(404, "Task does not exist");

    private int status;
    private String message;

    TaskErrorCode(int status, String message) {
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
