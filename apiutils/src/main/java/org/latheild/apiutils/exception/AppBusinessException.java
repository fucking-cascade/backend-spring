package org.latheild.apiutils.exception;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ErrorCode;

public class AppBusinessException extends BaseException {
    private static final ErrorCode DEFAULT_CODE = CommonErrorCode.INTERNAL_ERROR;

    public String code = DEFAULT_CODE.getCode();

    private int httpStatus = DEFAULT_CODE.getStatus();

    public AppBusinessException(String message) {
        super(message);
    }

    public AppBusinessException(String code, int httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public AppBusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), errorCode.getStatus(), message);
    }

    public AppBusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getExceptionType() {
        return this.getClass().getName();
    }
}
