package com.pacemaker.eta.domain.entity;

import com.pacemaker.eta.domain.global.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long kakaoId;

    private String accessToken;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    private String refreshToken;

    @Builder
    public Member(String name, Long kakaoId) {
        this.name = name;
        this.kakaoId = kakaoId;
    }

    public Set<UserRole> getAuthorities(){
        Set<UserRole> returnRole = new HashSet<>();
        returnRole.add(this.authority);
        return returnRole;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
