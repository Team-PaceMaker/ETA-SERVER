package com.pacemaker.eta.service;

import com.pacemaker.eta.domain.entity.Member;
import com.pacemaker.eta.global.exception.BusinessException;
import com.pacemaker.eta.global.exception.ErrorCode;
import com.pacemaker.eta.repository.MemberJpaRepository;
import dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberRepository;

    @Transactional
    public Member join(MemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw BusinessException.from(ErrorCode.MEMBER_DUPLICATE_EMAIL);
        }
        return memberRepository.save(request.toEntity());
    }

}