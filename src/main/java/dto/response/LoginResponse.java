package dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LoginResponse(Long memberId, String accessToken, @JsonIgnore String refreshToken) {
}
