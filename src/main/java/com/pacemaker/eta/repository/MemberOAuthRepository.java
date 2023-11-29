package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberOAuthRepository extends Repository<Member, Long> {

    Optional<Member> findByEmailAndRegistrationId(String email, String registrationId);
}

