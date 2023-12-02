package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Attention;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttentionJpaRepository extends JpaRepository<Attention, Long> {

    List<Attention> findAllByMemberId(Long memberId);

}
