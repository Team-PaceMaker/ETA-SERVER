package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
