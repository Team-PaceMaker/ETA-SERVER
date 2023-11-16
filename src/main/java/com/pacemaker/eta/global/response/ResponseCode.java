package com.pacemaker.eta.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버 내부 오류"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "요청을 잘못 보냈습니다."),
    PAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, false, "요청하신 페이지를 찾을 수 없습니다"),
    NEED_LOGIN(HttpStatus.BAD_REQUEST, false, "로그인이 필요합니다."),
    NO_CONTENT(HttpStatus.BAD_REQUEST, false, "데이터가 없습니다."),
    REPEATED_VALUE(HttpStatus.BAD_REQUEST, false, "중복된 데이터입니다."),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST, false, "권한이 없습니다"),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, false, "유저가 존재하지 않음"),
    TEAM_NOT_EXIST(HttpStatus.BAD_REQUEST, false, "팀이 존재하지 않음"),
    USER_NOT_TEAM_MEMBER(HttpStatus.BAD_REQUEST, false, "유저가 요청 대상 팀에 속해있지 않음"),
    INPUT_VALUE_FORMAT_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, false, "입력 값의 형식이 잘못됨"),

    // JWT
    NO_TOKEN(HttpStatus.OK, true, "TOKEN이 존재하지 않습니다"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, false, "TOKEN이 유효하지 않습니다"),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, false, "TOKEN이 만료되었습니다");

    // imaage

    //attention

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;
}
