package com.pacemaker.eta.controller;

import com.pacemaker.eta.service.AttentionService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my")
@RequiredArgsConstructor
public class DataController {

    private final AttentionService attentionService;

    @GetMapping("/day")
    public ResponseEntity<?> getDayAttentionTime(final Authentication authentication) {
        return ResponseEntity.ok(attentionService.getSevenDaysAttentionTime(authentication));
    }

//    @GetMapping("/week")
//    public ResponseEntity<?> getWeekAttentionTime(final Authentication authentication) {
//        return ResponseEntity.ok();
//    }
//
//    @GetMapping("/month")
//    public ResponseEntity<?> getMonthAttentionTime(final Authentication authentication) {
//        return ResponseEntity.ok();
//    }
}
