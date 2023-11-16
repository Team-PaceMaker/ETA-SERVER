package com.pacemaker.eta.controller;

import com.pacemaker.eta.service.AttentionService;
import dto.AttentionResponseDto;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<AttentionResponseDto> getStatus(@RequestParam("image") MultipartFile file) throws Exception {
        return ResponseEntity.ok(attentionService.getStatus(file));
    }
    @PostMapping("/in")
    public ResponseEntity<String> createAttention() {
        String createdAttentionId = attentionService.createAttention();
        URI location = URI.create("/api/v1/attention/in" + createdAttentionId);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/out/{attentionId}")
    public ResponseEntity<String> getTerminated(@PathVariable("attentionId") Long attentionId) {
        LocalDateTime stopAt = LocalDateTime.now();
        return ResponseEntity.ok(attentionService.stopAttention(attentionId, stopAt));
    }
}
