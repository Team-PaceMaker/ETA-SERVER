package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusJpaRepository extends JpaRepository<Status, Long> {

    List<Status> findAllByAttention_attentionId(Long attentionId);

}
