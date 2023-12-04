package com.pacemaker.eta.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum InternalServerExceptionType implements BaseException {
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","서버 내부에 문제가 발생하였습니다.");

    private final String errorCode;
    private final String message;

    InternalServerExceptionType(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}