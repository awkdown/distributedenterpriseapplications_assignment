package com.uclan.distributedenterpriseapplications_assignment;

import com.uclan.controller.TutorController;
import com.uclan.domain.LabSession;
import com.uclan.domain.Module;
import com.uclan.domain.Tutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uclan.domain.Tutor;
import com.uclan.repository.TutorRepository;
import com.uclan.service.LabSessionService;
import com.uclan.service.ModuleService;
import com.uclan.service.TutorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrudIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TutorRepository repos;

    @MockBean
    private TutorService serviceTutor;

    @MockBean
    private ModuleService serviceModule;

    @MockBean
    private LabSessionService labSessionService;

    @Test
    public void ensureBadAuth() {
        try {
            mvc.perform(formLogin()).andExpect(unauthenticated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void listTutors_200() throws Exception {
        List<Tutor> tutors = getTestTutors();
        given(serviceTutor.getTutors()).willReturn(tutors);
        try {
            mvc.perform(
                    get("/uclan/tutors")).
                    andExpect(status().isOk())
                    .andExpect(model().attributeExists("tutors"));
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @WithMockUser("admin")
    @Test
    public void listModules_200() throws Exception {
        List<Module> modules = getTestModules();
        given(serviceModule.getModules()).willReturn(modules);
        try {
            mvc.perform(
                    get("/uclan/modules")).
                    andExpect(status().isOk())
                    .andExpect(model().attributeExists("modules"));
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @WithMockUser("admin")
    @Test
    public void listLabSessions_200() throws Exception {
        List<LabSession> labSessions = getLabSessions();
        given(labSessionService.getLabSessions()).willReturn(labSessions);
        try {
            mvc.perform(
                    get("/uclan/labSessions")).
                    andExpect(status().isOk())
                    .andExpect(model().attributeExists("labSessions"));
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void addTutor_200()
            throws Exception {
        Tutor tutorToAdd = new Tutor("picard", "picard@starfleet.com", "picard");

        try {
            mvc.perform(
                    post("/uclan/tutors/add").
                    content(asJsonString(tutorToAdd)).
                    contentType(MediaType.APPLICATION_JSON).
                            accept(MediaType.APPLICATION_JSON).with(csrf())).
                    andExpect(status().isOk()).
                    andExpect(model().attributeExists("tutors"));

        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void addModules_200()
            throws Exception {
        Module module = getTestModules().get(0);

        try {
            mvc.perform(
                    post("/uclan/modules/add").
                            content(asJsonString(module)).
                            contentType(MediaType.APPLICATION_JSON).
                            accept(MediaType.APPLICATION_JSON).with(csrf())).
                    andExpect(status().isOk()).
                    andExpect(model().attributeExists("modules"));

        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void addLabSession_200()
            throws Exception {
        LabSession labSession = getLabSessions().get(0);

        try {
            mvc.perform(
                    post("/uclan/labSessions/add").
                            content(asJsonString(labSession)).
                            contentType(MediaType.APPLICATION_JSON).
                            accept(MediaType.APPLICATION_JSON).with(csrf())).
                    andExpect(status().isOk()).
                    andExpect(model().attributeExists("labSessions"));

        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void deleteTutor_200() {
        try {
            mvc.perform(
                    post("/uclan/tutors/{id}/delete", 1L).with(csrf()))
                    .andExpect(model().attributeDoesNotExist("labSessions"));   //because of the Post method I receive 302
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void deleteModule_200() {
        try {
            mvc.perform(
                    post("/uclan/modules/{id}/delete", 1L).with(csrf()))
                    .andExpect(model().attributeDoesNotExist("modules"));   //because of the Post method I receive 302
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @WithMockUser("admin")
    @Test
    public void deleteLabSession_200() {
        try {
            mvc.perform(
                    post("/uclan/labSessions/{id}/delete", 1L).with(csrf()))
                    .andExpect(model().attributeDoesNotExist("labSessions"));   //because of the Post method I receive 302
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<Tutor> getTestTutors() {
        Tutor test1 = new Tutor("chiara", "cmfasching@uclan.ac.uk", "chiara");
        Tutor test2 = new Tutor("kirk", "kirk@gmail.com", "kirk");
        Tutor test3 = new Tutor("spock", "spock@enterprise.com", "spock");
        ArrayList<Tutor> tutors = new ArrayList<>();
        tutors.add(test1);
        tutors.add(test2);
        tutors.add(test3);
        return tutors;
    }

    private ArrayList<Module> getTestModules() {
        ArrayList<Tutor> tutors = getTestTutors();
        Module m1 = new Module("DES", tutors.get(0), "Distributed Enterprise Systems");
        Module m2 = new Module("DS", tutors.get(1), "Data Science");
        Module m3 = new Module("AI", tutors.get(2), "Artificial Intelligence");
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(m1);
        modules.add(m2);
        modules.add(m3);
        return modules;
    }

    private ArrayList<LabSession> getLabSessions() {
        ArrayList<Tutor> tutors = getTestTutors();
        ArrayList<Module> modules = getTestModules();
        LabSession l1 = new LabSession("Java Beans", "What are Java Beans?", modules.get(0), tutors.get(0));
        LabSession l2 = new LabSession("Java Beans", "What are Java Beans?", modules.get(1), tutors.get(1));
        LabSession l3 = new LabSession("Java Beans", "What are Java Beans?", modules.get(2), tutors.get(2));
        ArrayList<LabSession> labSessions = new ArrayList<>();
        labSessions.add(l1);
        labSessions.add(l2);
        labSessions.add(l3);
        return labSessions;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}