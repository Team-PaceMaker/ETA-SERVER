package com.pacemaker.eta.auth.jwt;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshToken {

    private String value;
    private LocalDateTime expiredTime;
    private Long memberId;

    public boolean isValid(LocalDateTime now) {
        return now.isBefore(expiredTime);
    }
}

