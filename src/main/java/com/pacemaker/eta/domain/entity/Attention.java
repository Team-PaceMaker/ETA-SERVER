package com.pacemaker.eta.domain.entity;

import com.pacemaker.eta.domain.global.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "attention")
@EntityListeners(AuditingEntityListener.class)
public class Attention extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attention_id")
    private Long attentionId;

    @Column(name = "stop_at")
    private LocalDateTime stopAt;

    @OneToMany(mappedBy = "attention", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();
    public void setStopAt(LocalDateTime stopAt) {
        this.stopAt = stopAt;
    }

}
