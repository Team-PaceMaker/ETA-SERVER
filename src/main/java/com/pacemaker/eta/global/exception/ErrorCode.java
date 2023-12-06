package com.pacemaker.eta.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MEMBER_DUPLICATE_EMAIL(1001, 400, "이미 가입된 사용자의 이메일입니다."),
    NOT_FOUND_REFRESH_TOKEN_IN_COOKIE(2001, 401, "엑세스 토큰 재발급을 위한 리프레시 토큰이 존재하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN_IN_REPOSITORY(2002, 401, "리프레시 토큰 저장소에 존재하지 않는 리프레시 토큰입니다."),

    EXPIRED_REFRESH_TOKEN(2003, 401, "리프레시 토큰이 만료되었습니다"),
    AUTHORIZATION_FAIL(2004, 401, "엑세스 토큰이 존재하지 않거나 만료되었습니다."),
    NOT_FOUND_AUTHORITY(2005, 401, "권한이 없는 사용자입니다."),
    NOT_FOUND_USER(2006, 401, "찾을 수 없는 사용자입니다."),
    LOGOUT_USER(2007, 401, "로그아웃 한 유저입니다."),
    INVALID_TOKEN(2008, 401, "유효하지 않은 토큰 형식입니다."),

    INVALID_WEEK(3001, 401, "1~3주차까지 조회가 가능합니다."),
    NOT_FOUND_ATTENTION(3002, 401, "해당 id의 집중을 찾을 수 없습니다."),
    NOT_ENDING_ETA(3003, 401, "아직 종료되지 않은 ETA 입니다.")
    ;

    private final int value;
    private final int httpStatusCode;
    private final String message;

    ErrorCode(int value, int httpStatusCode, String message) {
        this.value = value;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}

