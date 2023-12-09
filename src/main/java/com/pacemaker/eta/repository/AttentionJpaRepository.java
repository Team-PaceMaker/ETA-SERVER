package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Attention;
import com.pacemaker.eta.domain.entity.Member;
import com.pacemaker.eta.global.exception.BusinessException;
import com.pacemaker.eta.global.exception.ErrorCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttentionJpaRepository extends JpaRepository<Attention, Long> {

    List<Attention> findAllByMemberId(Long memberId);

    default Attention findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
            () -> BusinessException.from(ErrorCode.NOT_FOUND_USER));
    }

}
