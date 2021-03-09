package com.uclan.distributedenterpriseapplications_assignment;

import com.uclan.domain.LabSession;
import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import com.uclan.repository.LabSessionRepository;
import com.uclan.repository.ModuleReporsitory;
import com.uclan.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ErrorIntegrationTest {

    @Autowired
    private TutorRepository reposTutor;

    @Autowired
    private ModuleReporsitory reposModule;

    @Autowired
    private LabSessionRepository reposLabSession;

    @Test
    public void addTutorInegrityViolation() {
        Tutor t1 = new Tutor("picard", "picard@starfleet.com", "picard");
        Tutor t2 = new Tutor("picard", "picard@starfleet.com", "picard");

        reposTutor.saveAll(Arrays.asList(t1, t2));
        assertThrows(DataIntegrityViolationException.class, () -> reposTutor.flush());
    }

    @Test
    public void addLabSessionTutorInegrityViolation() {
        Tutor t1 = new Tutor("picard", "picard@starfleet.com", "picard");

        reposTutor.saveAndFlush(t1);

        Module m1 = new Module("DES", t1, "Distributed Enterprise Systems");

        reposModule.saveAndFlush(m1);

        LabSession l1 = new LabSession("Java Beans", "What are Java Beans?", m1, t1);
        LabSession l2 = new LabSession("JSF", "What is JSF?", m1, t1);

        reposLabSession.saveAll(Arrays.asList(l1, l2));
        assertThrows(DataIntegrityViolationException.class, () -> reposTutor.flush());
    }

    @Test
    public void addModuleTutorIntegrityViolation() {
        Tutor t1 = new Tutor("picard", "picard@starfleet.com", "picard");

        reposTutor.saveAndFlush(t1);

        Module m1 = new Module("DES", t1, "Distributed Enterprise Systems");
        Module m2 = new Module("DS", t1, "Data Science");

        reposModule.saveAll(Arrays.asList(m1, m2));
        assertThrows(DataIntegrityViolationException.class, () -> reposModule.flush());
    }
}
