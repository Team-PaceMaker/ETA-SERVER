package com.pacemaker.eta.domain.entity;

import com.pacemaker.eta.domain.global.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "status")
@NoArgsConstructor
@Getter
public class Status {

    private static final int NOT_ATTENTION = 0;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attention_id")
    private Attention attention;

    @Column(name="current_status")
    private int currentStatus;


    @Column(name = "captured_at")
    private LocalDateTime capturedAt;

    @Builder
    public Status(int currentStatus, LocalDateTime capturedAt, Attention attention) {
        this.currentStatus = currentStatus;
        this.capturedAt = capturedAt;
        this.attention = attention;
    }
}
