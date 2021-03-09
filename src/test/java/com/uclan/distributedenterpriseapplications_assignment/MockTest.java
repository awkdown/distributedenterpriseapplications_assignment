package com.uclan.distributedenterpriseapplications_assignment;

import com.uclan.domain.LabSession;
import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import com.uclan.repository.LabSessionRepository;
import com.uclan.repository.ModuleReporsitory;
import com.uclan.repository.TutorRepository;
import com.uclan.service.LabSessionService;
import com.uclan.service.ModuleService;
import com.uclan.service.TutorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@Configuration
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class MockTest {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public BCryptPasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }
    }

    private TutorService tutorService;

    @MockBean
    private TutorRepository tutorRepository;

    private ModuleService moduleService;

    @MockBean
    private ModuleReporsitory moduleReporsitory;

    private LabSessionService labSessionService;

    @MockBean
    private LabSessionRepository labSessionRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @BeforeAll
    void setup() {

        assertThat(tutorRepository).isNotNull();
        assertThat(moduleReporsitory).isNotNull();
        assertThat(labSessionRepository).isNotNull();
        tutorService = new TutorService(tutorRepository, encoder);
        moduleService = new ModuleService(moduleReporsitory, null);
        labSessionService = new LabSessionService(labSessionRepository, null);
    }

    @Test
    public void getAllTutors() {
        tutorService.getTutors();
        Mockito.verify(tutorRepository).findAll();
    }

    @Test
    public void getAllModules() {
        moduleService.getModules();
        Mockito.verify(moduleReporsitory).findAll();
    }

    @Test
    public void getAllLabSessions() {
        labSessionService.getLabSessions();
        Mockito.verify(labSessionRepository).findAll();
    }

    @Test
    public void createTutor() {
        tutorService.createTutor(getTutuor());
        Mockito.verify(tutorRepository).save(Mockito.any());
    }

    @Test
    public void crateModule() {
        moduleService.createModule(getModule());
        Mockito.verify(moduleReporsitory).save(Mockito.any());
    }

    @Test
    public void createLabSession() {
        labSessionService.createLabSession(getLabSession());
        Mockito.verify(labSessionRepository).save(Mockito.any());
    }

    @Test
    public void deleteTutor() {
        tutorService.delete(1L);
        Mockito.verify(tutorRepository).deleteById(Mockito.any());
    }

    @Test
    public void deleteModule() {
        moduleService.delete(1L);
        Mockito.verify(moduleReporsitory).deleteById(Mockito.any());
    }

    @Test
    public void deleteLabSession() {
        labSessionService.delete(1L);
        Mockito.verify(labSessionRepository).deleteById(Mockito.any());
    }

    private Tutor getTutuor() {
        return new Tutor("spock", "spock@enterprise.com", "spock");
    }

    private Module getModule() {
        Tutor tutor = getTutuor();
        return new Module("DES", tutor, "Distributed Enterprise Systems");
    }

    private LabSession getLabSession() {
        Tutor tutor = getTutuor();
        Module module = getModule();
        return new LabSession("Java Beans", "What are Java Beans?", module, tutor);
    }
}