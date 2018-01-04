package org.latheild.apiutils.api;

public enum CommonErrorCode implements ErrorCode {
    SUCCESS(200, "Success request"),
    BAD_REQUEST(400, "Bad request"),
    INVALID_ARGUMENT(400, "Invalid argument"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    NOT_ACCEPTABLE(406, "Request not acceptable"),
    CONFLICT(409, "Resource conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported media type"),
    INTERNAL_ERROR(500, "Internal error"),
    SERVICE_UNAVAILABLE(503, "Service unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway timeout");

    private int status;
    private String message;

    CommonErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static CommonErrorCode fromHttpStatus(int httpStatus) {
        for (CommonErrorCode errorCode : values()) {
            if (errorCode.getStatus() == httpStatus) {
                return errorCode;
            }
        }
        return INTERNAL_ERROR;
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
