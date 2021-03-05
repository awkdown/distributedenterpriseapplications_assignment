package com.uclan.service;

import com.sun.istack.NotNull;
import com.uclan.domain.Tutor;
import com.uclan.repository.ModuleReporsitory;
import com.uclan.repository.TutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


@Service
@Transactional
public class ModuleService {
    private final ModuleReporsitory moduleReporsitory;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PasswordEncoder encoder;

    public ModuleService(ModuleReporsitory moduleReporsitory, PasswordEncoder encoder) {
        this.moduleReporsitory = moduleReporsitory;
        this.encoder = encoder;
    }

    public List<Module> getModules() {
        logger.debug("Retrieving {} tutor from com.uclan.repository", moduleReporsitory.count());
        return moduleReporsitory.findAll();
    }

    public Module createModule(@NotNull @Valid Module module) {
        return moduleReporsitory.save(module);
    }
}