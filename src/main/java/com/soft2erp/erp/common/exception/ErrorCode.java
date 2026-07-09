package com.soft2erp.erp.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    BUSINESS_ERROR(HttpStatus.BAD_REQUEST, "BUSINESS_ERROR"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYSTEM_ERROR");

    private final HttpStatus status;
    private final String code;

    ErrorCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    public HttpStatus status() {
        return status;
    }

    public String code() {
        return code;
    }
}

