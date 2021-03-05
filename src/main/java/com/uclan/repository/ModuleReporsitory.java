package com.uclan.repository;

import com.uclan.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleReporsitory extends JpaRepository<Module, Long> {
}
