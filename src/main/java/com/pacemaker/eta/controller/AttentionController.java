package com.pacemaker.eta.controller;

import com.pacemaker.eta.service.AttentionService;
import dto.response.AttentionOutResponseDto;
import dto.response.AttentionResponseDto;
import dto.response.AttentionTimeSlotResponseDto;
import dto.response.DonutChartResponseDto;
import dto.response.RecordResponseDto;
import dto.response.StatusResponseDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attention")
public class AttentionController {

    private final AttentionService attentionService;

    @PostMapping
    public ResponseEntity<StatusResponseDto> getStatus(@RequestParam("image") MultipartFile file,
        @RequestParam Long attentionId) throws Exception {
        return ResponseEntity.ok(attentionService.getStatus(file, attentionId));
    }
    @PostMapping("/in")
    public ResponseEntity<AttentionResponseDto> createAttention() {
        Long createdAttentionId = attentionService.createAttention();
        AttentionResponseDto responseBody = new AttentionResponseDto(createdAttentionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/out/{attentionId}")
    public ResponseEntity<AttentionOutResponseDto> getTerminated(
        @PathVariable("attentionId") Long attentionId) {
        LocalDateTime stopAt = LocalDateTime.now();
        AttentionOutResponseDto responseDto = attentionService.stopAttention(attentionId, stopAt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
    }

    @GetMapping("/record/{attentionId}")
    public RecordResponseDto getRecord(
        @PathVariable("attentionId") Long attentionId) {
        return attentionService.getRecord(attentionId);
    }

    @GetMapping("/record/count/{attentionId}")
    public DonutChartResponseDto getAttentionCount(@PathVariable("attentionId") Long attentionID) {
        return attentionService.getAttentionCount(attentionID);
    }

    @GetMapping("/{attentionId}")
    public StatusResponseDto getRecentStatus(@PathVariable("attentionId") Long attentionId) {
        return attentionService.getFiveMinutesPrediction(attentionId);
    }

}
