package com.uclan.repository;

import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleReporsitory extends JpaRepository<Module, Long> {
    Optional<Module> findByName(String name);
    Optional<Module> findByModuleLeader(Tutor moduleLeader);
}
