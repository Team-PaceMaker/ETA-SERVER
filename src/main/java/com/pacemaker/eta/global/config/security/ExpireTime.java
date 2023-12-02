package com.pacemaker.eta.global.config.security;

public class ExpireTime {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 5 * 2 * 30 * 60 * 1000L;       // 5시간 (테스트)
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;     //7일
}

