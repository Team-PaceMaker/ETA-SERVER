package dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ReIssueResponse(Long memberId, String accessToken, @JsonIgnore String refreshToken) {
}
