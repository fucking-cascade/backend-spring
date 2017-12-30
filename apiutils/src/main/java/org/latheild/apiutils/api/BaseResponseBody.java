package org.latheild.apiutils.api;

import java.util.Date;

public class BaseResponseBody {
    private final Long timestamp;

    private int status;

    private Object message;

    public BaseResponseBody(ErrorCode errorCode, Object message) {
        timestamp = new Date().getTime();
        this.status = errorCode.getStatus();
        this.message = message;
    }

    public BaseResponseBody(ErrorCode errorCode) {
        timestamp = new Date().getTime();
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public BaseResponseBody(int status, Object message) {
        timestamp = new Date().getTime();
        this.status = status;
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
