package org.latheild.apiutils.api;

import org.latheild.apiutils.exception.Exception;

public class ExceptionResponseBody extends BaseResponseBody {
    private String error;

    private String exception;

    public ExceptionResponseBody(ErrorCode errorCode, String exceptionType, String message) {
        super(errorCode, message);
        this.error = errorCode.getCode();
        this.exception = exceptionType;
    }

    public ExceptionResponseBody(ErrorCode errorCode, String exceptionType) {
        super(errorCode);
        this.error = errorCode.getCode();
        this.exception = exceptionType;
    }

    public ExceptionResponseBody(int status, String error, String exceptionType, String message) {
        super(status, message);
        this.error = error;
        this.exception = exceptionType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ExceptionResponseBody{" +
                "error='" + error + '\'' +
                ", exception='" + exception + '\'' +
                '}';
    }
}
