package dto.request;

import com.pacemaker.eta.domain.entity.Member;

public record MemberRequest(String registrationId, String name, String email, String pictureUrl) {
    public Member toEntity() {
        return new Member(name, email);
    }
}

