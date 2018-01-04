package org.latheild.project.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum ProjectErrorCode implements ErrorCode {
    ProjectNotExist(404, "Project does not exist");

    private int status;
    private String message;

    ProjectErrorCode(int status, String message) {
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
