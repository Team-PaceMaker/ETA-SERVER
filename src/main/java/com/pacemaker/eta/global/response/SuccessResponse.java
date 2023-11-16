package com.pacemaker.eta.global.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

@SuperBuilder
@Getter
@Setter
public class SuccessResponse extends BasicResponse {

    private Object detail;

    public static ResponseEntity<BasicResponse> toResponseEntity(ResponseCode responseCode, Object data) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(SuccessResponse.builder()
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .detail(data)
                        .build()
                );
    }
}