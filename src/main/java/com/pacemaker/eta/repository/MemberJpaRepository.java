package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Member;
import com.pacemaker.eta.global.exception.BusinessException;
import com.pacemaker.eta.global.exception.ErrorCode;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByKakaoId(Long kakaoId);

    default Member findByKakaoIdOrThrow(Long id) {
        return findByKakaoId(id).orElseThrow(
            () -> BusinessException.from(ErrorCode.NOT_FOUND_USER));
    }

    Member findByAccessToken(String accessToken);

}
