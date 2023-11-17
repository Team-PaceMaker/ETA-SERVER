package com.pacemaker.eta.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pacemaker.eta.domain.entity.Status;
import com.pacemaker.eta.repository.StatusJpaRepository;
import dto.response.AttentionTimeSlotResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttentionServiceTest {

    @Test
    public void 현재_시각_테스트() {
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.print(currentTime);
    }
}
