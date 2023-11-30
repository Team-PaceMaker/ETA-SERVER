package com.pacemaker.eta.domain.entity;

import com.pacemaker.eta.domain.global.BaseTimeEntity;
import java.time.LocalDateTime;
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

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String refreshToken;

    // private String registrationId;

    @Builder
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
