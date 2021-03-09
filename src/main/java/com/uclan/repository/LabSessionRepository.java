package com.uclan.repository;

import com.uclan.domain.LabSession;
import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabSessionRepository extends JpaRepository<LabSession, Long> {
    Optional<LabSession> findBySessionLeader(Tutor sessionLeader);
    Optional<LabSession> findByName(String name);
}
