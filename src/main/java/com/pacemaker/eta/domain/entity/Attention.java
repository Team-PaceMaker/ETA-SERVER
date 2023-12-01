package com.pacemaker.eta.domain.entity;

import com.pacemaker.eta.domain.global.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "attention")
@EntityListeners(AuditingEntityListener.class)
public class Attention extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attention_id")
    private Long attentionId;

    @Column(name = "stop_at")
    private LocalDateTime stopAt;

    @OneToMany(mappedBy = "attention", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Attention(Member member) {
        this.member = member;
    }

    public void setStopAt(LocalDateTime stopAt) {
        this.stopAt = stopAt;
    }

    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

}
