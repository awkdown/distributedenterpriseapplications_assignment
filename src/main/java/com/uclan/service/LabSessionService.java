package com.uclan.service;

import com.sun.istack.NotNull;
import com.uclan.domain.LabSession;
import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import com.uclan.repository.LabSessionRepository;
import com.uclan.repository.ModuleReporsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public class LabSessionService {
    private final LabSessionRepository labSessionReporsitory;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PasswordEncoder encoder;

    public LabSessionService(LabSessionRepository labSessionReporsitory, PasswordEncoder encoder) {
        this.labSessionReporsitory = labSessionReporsitory;
        this.encoder = encoder;
    }

    public List<LabSession> getLabSessions() {
        logger.debug("Retrieving {} tutor from com.uclan.repository", labSessionReporsitory.count());
        return labSessionReporsitory.findAll();
    }

    public LabSession getLabSessionByName(String name) {
        LabSession labSession = null;
        try {
            labSession =  labSessionReporsitory.findByName(name).orElseThrow();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return labSession;
    }

    public LabSession getLabSessionById(Long id) {
        LabSession labSession = null;
        try {
            labSession =  labSessionReporsitory.findById(id).orElseThrow();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return labSession;
    }

    public LabSession createLabSession(@NotNull @Valid LabSession labSession) {

        return labSessionReporsitory.save(labSession);
    }

    public LabSession getLebSessionBySessionLeader(@NotNull @Valid Tutor sessionLeader) {
        LabSession labSession = null;
        try {
            labSession = labSessionReporsitory.findBySessionLeader(sessionLeader).orElseThrow();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return labSession;
    }

    public void delete(long id) {
        LabSession lab = labSessionReporsitory.findById(id).orElseThrow();
        labSessionReporsitory.deleteById(lab.getId());
    }

    public void update( Long id, @NotNull @Valid LabSession labSession) {
        LabSession labSessionToUpdate = labSessionReporsitory.findById(id).orElseThrow();
        labSessionToUpdate.setName(labSession.getName());
        labSessionToUpdate.setModule(labSession.getModule());
        labSessionToUpdate.setDescription(labSession.getDescription());
    }
}