package com.pacemaker.eta.controller;

import com.pacemaker.eta.global.exception.BusinessException;
import com.pacemaker.eta.global.exception.ErrorCode;
import com.pacemaker.eta.service.AttentionService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my")
@RequiredArgsConstructor
public class DataController {

    private final AttentionService attentionService;

    @GetMapping("/day/{week}")
    public ResponseEntity<?> getDayAttentionTime(
        @PathVariable(value = "week") int week,
        final Authentication authentication) {
        if (week < 1 || week > 3) {
            throw BusinessException.from(ErrorCode.INVALID_WEEK);
        }

        return ResponseEntity.ok(attentionService.getSevenDaysAttentionTime(authentication, week));
    }
}
