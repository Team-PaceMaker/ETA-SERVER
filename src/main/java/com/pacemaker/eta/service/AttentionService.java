package com.pacemaker.eta.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacemaker.eta.domain.entity.Attention;
import com.pacemaker.eta.domain.entity.Status;
import com.pacemaker.eta.repository.AttentionJpaRepository;
import com.pacemaker.eta.repository.StatusJpaRepository;
import dto.AttentionResponseDto;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private static final String ATTENTION_MODEL_URL = "http://eta-model.kro.kr:5000/api/v1/eta/attention";

    private final AttentionJpaRepository attentionJpaRepository;
    private final StatusJpaRepository statusJpaRepository;

    @Transactional
    public String createAttention() {
        Attention attention = new Attention();
        attentionJpaRepository.save(attention);
        return attention.getAttentionId().toString();
    }

    @Transactional
    public String stopAttention(Long attentionId, LocalDateTime stopAt) {
        Attention attention = attentionJpaRepository.findById(attentionId)
            .orElseThrow(() -> new EntityNotFoundException(("해당하는 집중 ID를 찾을 수 없습니다.")));
        attention.setStopAt(stopAt);
        return attention.getStopAt().toString();
    }
    private void handlePrediction(String responseBody) {
        int prediction = getPrediction(responseBody);
        createStatus(prediction);
    }

    private int getPrediction(String responseBody) {
        int prediction;
        if(responseBody.contains("0")) {
            prediction = 0;
            return prediction;
        }
        if (responseBody.contains("1")) {
            prediction = 1;
            return prediction;
        }
        return -1;
    }

    @Transactional
    public String createStatus(int prediction) {
        // Long attentionId = attentionJpaRepository.
        Status status = statusJpaRepository.save(Status.builder()
                .currentStatus(prediction)
                .capturedAt(LocalDateTime.now())
                .build());
        return status.getStatusId().toString();
    }

    @Transactional
    public AttentionResponseDto getStatus(MultipartFile file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        HttpEntity<String> response = restTemplate.postForEntity(ATTENTION_MODEL_URL, requestMessage, String.class);

        handlePrediction(Objects.requireNonNull(response.getBody()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return objectMapper.readValue(response.getBody(), AttentionResponseDto.class);
    }

}
