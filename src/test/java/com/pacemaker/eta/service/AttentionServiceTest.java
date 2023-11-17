package com.pacemaker.eta.service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttentionServiceTest {

    @Test
    public void 현재_시각_테스트() {
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.print(currentTime);
    }


}
