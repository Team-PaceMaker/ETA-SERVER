package com.pacemaker.eta.global.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    private static final String KST = "Asia/Seoul";

    @PostConstruct
    public void init() { TimeZone.setDefault(TimeZone.getTimeZone(KST));}
}
