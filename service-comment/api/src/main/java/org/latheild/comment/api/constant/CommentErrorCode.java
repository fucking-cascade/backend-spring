package org.latheild.comment.api.constant;

import org.latheild.apiutils.api.ErrorCode;

public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_EXIST(404, "Comment does not exist");

    private int status;
    private String message;

    CommentErrorCode(int status, String message) {
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
