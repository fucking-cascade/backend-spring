package org.latheild.relation.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum RelationErrorCode implements ErrorCode {
    RELATION_NOT_EXIST(404, "Relation does not exist"),
    RELATION_EXIST(409, "Relation already exists");

    private int status;
    private String message;

    RelationErrorCode(int status, String message) {
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
