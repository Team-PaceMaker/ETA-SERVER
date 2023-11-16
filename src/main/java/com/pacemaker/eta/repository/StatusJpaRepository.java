package com.pacemaker.eta.repository;

import com.pacemaker.eta.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusJpaRepository extends JpaRepository<Status, Long> {

}
