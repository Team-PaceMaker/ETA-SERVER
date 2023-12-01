package com.pacemaker.eta.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacemaker.eta.domain.entity.Attention;
import com.pacemaker.eta.domain.entity.Member;
import com.pacemaker.eta.domain.entity.Status;
import com.pacemaker.eta.repository.AttentionJpaRepository;
import com.pacemaker.eta.repository.MemberJpaRepository;
import com.pacemaker.eta.repository.StatusJpaRepository;
import dto.response.AttentionOutResponseDto;
import dto.response.AttentionTimeSlotResponseDto;
import dto.response.DonutChartResponseDto;
import dto.response.RecordResponseDto;
import dto.response.StatusResponseDto;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttentionService {

    private static final String ATTENTION_MODEL_URL = "http://eta-model.kro.kr:5001/api/v1/eta/attention";
    private static final int ATTENTION_STATUS = 1;
    private static final int DISTRACTION_STATUS = 0;

    private final AttentionJpaRepository attentionJpaRepository;
    private final StatusJpaRepository statusJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public Long createAttention(Authentication authentication) {
        long userKakaoId = Long.parseLong(authentication.getName());
        Member member = memberJpaRepository.findByKakaoIdOrThrow(userKakaoId);
        Attention attention = Attention.builder()
            .member(member).build();
        attentionJpaRepository.save(attention);
        return attention.getAttentionId();
    }

    @Transactional
    public AttentionOutResponseDto stopAttention(Long attentionId, LocalDateTime stopAt) {
        Attention attention = attentionJpaRepository.findById(attentionId)
            .orElseThrow(() -> new EntityNotFoundException(("해당하는 집중 ID를 찾을 수 없습니다.")));
        attention.setStopAt(stopAt);
        return AttentionOutResponseDto.of(attention);
    }

    public DonutChartResponseDto getAttentionCount(Long attentionId) {
        int[] attentionOrNot = getStatusCount(attentionId);
        int distractionCnt = attentionOrNot[0];
        int attentionCnt = attentionOrNot[1];
        return new DonutChartResponseDto(distractionCnt, attentionCnt);
    }

    public RecordResponseDto getRecord(Long attentionId) {
        Attention attention = attentionJpaRepository.findById(attentionId)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 집중 ID를 찾을 수 없습니다."));

        if (attention.getStopAt() == null) {
            throw new IllegalArgumentException("[Error] 종료되지 않은 attention입니다.");
        }
        Duration interval = getTotalTime(attention.getCreatedAt(), attention.getStopAt());

        List<LocalDateTime> attentionTimeList = getAttentionTimeList(attentionId);
        Duration attentionDuration = getAttentionTime(attentionTimeList);

        AttentionTimeSlotResponseDto timeSlots = getAttentionSlots(attentionTimeList);

        return new RecordResponseDto(interval, attentionDuration, timeSlots);
    }

    private Duration getTotalTime(LocalDateTime startAt, LocalDateTime stopAt) {
        return Duration.between(startAt, stopAt);
    }

    private int[] getStatusCount(Long attentionId) {
        List<Status> statusList = statusJpaRepository.findAllByAttention_attentionId(attentionId);
        int distractionCount = 0;
        int attentionCount = 0;
        for (Status status : statusList) {
            if (status.getCurrentStatus() == 1) {
                attentionCount++;
            } else {
                distractionCount++;
            }
        }
        return new int[]{distractionCount, attentionCount};
    }

    private void handlePrediction(String responseBody, Long attentionId) {
        int prediction = getPrediction(responseBody);
        createStatus(prediction, attentionId);
    }

    private int getPrediction(String responseBody) {
        int prediction;
        if (responseBody.contains("0")) {
            prediction = DISTRACTION_STATUS;
            return prediction;
        }
        if (responseBody.contains("1")) {
            prediction = ATTENTION_STATUS;
            return prediction;
        }
        return -1;
    }

    @Transactional
    public String createStatus(int prediction, Long attentionId) {
        Attention attention = attentionJpaRepository.findById(attentionId).orElseThrow(
            () -> new IllegalArgumentException("유효하지 않은 attention_id: " + attentionId));
        Status status = statusJpaRepository.save(
            Status.builder().currentStatus(prediction).capturedAt(LocalDateTime.now())
                .attention(attention).build());
        return status.getStatusId().toString();
    }

    @Transactional
    public StatusResponseDto getStatus(MultipartFile file, Long attentionId) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        HttpEntity<String> response = restTemplate.postForEntity(ATTENTION_MODEL_URL,
            requestMessage, String.class);

        handlePrediction(Objects.requireNonNull(response.getBody()), attentionId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return objectMapper.readValue(response.getBody(), StatusResponseDto.class);
    }

    private List<LocalDateTime> getAttentionTimeList(Long attentionId) {
        List<LocalDateTime> attentionTimeList = new ArrayList<>();
        List<Status> statusList = statusJpaRepository.findAllByAttention_attentionId(attentionId);
        for (Status status : statusList) {
            LocalDateTime capturedAt = status.getCapturedAt();
            if (capturedAt != null && status.getCurrentStatus() == ATTENTION_STATUS) {
                attentionTimeList.add(status.getCapturedAt());
            }
        }
        return attentionTimeList;
    }

    private Duration getAttentionTime(List<LocalDateTime> attentionList) {
        long totalSeconds = attentionList.size();
        return Duration.ofSeconds(totalSeconds);
    }

    public StatusResponseDto getFiveMinutesPrediction(Long attentionId) {
        int attentions = 0;
        int distractions = 0;

        List<Status> statusGroup = statusJpaRepository.findAllByAttention_attentionId(attentionId);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = currentTime.minusMinutes(5);
        for (Status status : statusGroup) {
            Duration interval = Duration.between(status.getCapturedAt(), currentTime);
            LocalDateTime statusTime = status.getCapturedAt();
            if (interval.getSeconds() <= 300 && statusTime.isAfter(fiveMinutesAgo)
                && status.getCapturedAt()
                .isBefore(currentTime)) {
                if (status.getCurrentStatus() == 1) {
                    attentions++;
                } else {
                    distractions++;
                }
            }
        }

        if (attentions >= distractions) {
            return new StatusResponseDto(ATTENTION_STATUS);
        } else {
            return new StatusResponseDto(DISTRACTION_STATUS);
        }
    }

    public AttentionTimeSlotResponseDto getAttentionSlots(List<LocalDateTime> attentionTimeList) {
        if (attentionTimeList == null || attentionTimeList.isEmpty()) {
            return new AttentionTimeSlotResponseDto(Collections.emptyList());
        }

        Map<Integer, Long> frequencyMap = attentionTimeList.stream()
            .collect(Collectors.groupingBy(
                LocalDateTime::getHour,
                Collectors.counting()
            ));

        OptionalLong maxFrequency = frequencyMap.values().stream().mapToLong(v -> v).max();
        List<String> resultSlots = new ArrayList<>();

        if (maxFrequency.isPresent()) {
            List<Integer> mostFrequentHours = frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency.getAsLong())
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

            int startHour = mostFrequentHours.get(0);
            int endHour = startHour;

            for (int i = 1; i < mostFrequentHours.size(); i++) {
                if (mostFrequentHours.get(i) - endHour == 1) {
                    endHour = mostFrequentHours.get(i);
                } else {
                    resultSlots.add(formatSlot(startHour, endHour));
                    startHour = mostFrequentHours.get(i);
                    endHour = startHour;
                }
            }
            resultSlots.add(formatSlot(startHour, endHour));
        }

        return new AttentionTimeSlotResponseDto(resultSlots);
    }

    private String formatSlot(int startHour, int endHour) {
        return startHour + "-" + (endHour+1);
    }

}
