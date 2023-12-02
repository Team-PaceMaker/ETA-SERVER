package com.pacemaker.eta.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pacemaker.eta.domain.entity.Status;
import com.pacemaker.eta.repository.AttentionJpaRepository;
import com.pacemaker.eta.repository.StatusJpaRepository;
import dto.response.AttentionTimeSlotResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;


public class AttentionServiceTest {

    @Test
    public void 현재_시각_테스트() {
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.print(currentTime);
    }

    @Test
    public void 오늘_날짜_테스트() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate previousDates = today.minusDays(i);
            System.out.println(previousDates);
        }

    }
}
