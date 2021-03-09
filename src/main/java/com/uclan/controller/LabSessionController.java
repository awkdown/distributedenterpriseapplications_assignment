package com.uclan.controller;

import com.uclan.domain.LabSession;
import com.uclan.service.LabSessionService;
import com.uclan.service.ModuleService;
import com.uclan.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LabSessionController {
    private static final String MODEL_Module = "modules";
    private static final String MODEL_Tutors = "tutors";
    private static final String MODEL_LABSESSIONS = "labSessions";
    private final ModuleService moduleService;
    private final TutorService tutorService;
    private final LabSessionService labSessionService;


    public LabSessionController(ModuleService moduleService, TutorService tutorService, LabSessionService labSessionService) {
        this.moduleService = moduleService;
        this.tutorService = tutorService;
        this.labSessionService = labSessionService;
    }

    @ModelAttribute("labSession")
    public LabSession getModule() {
        return new LabSession();
    }

    @GetMapping("/uclan/labSessions")
    public String getLabSessions(Model model) {
        model.addAttribute(MODEL_Tutors, tutorService.getTutors());
        model.addAttribute(MODEL_Module, moduleService.getModules());
        model.addAttribute(MODEL_LABSESSIONS, labSessionService.getLabSessions());
        return "show-labSessions";
    }

    @PostMapping("/uclan/labSessions/add")
    public String insertLabSession(@ModelAttribute @Valid LabSession labSession, BindingResult result, Model model) {
        if (labSessionService.getLabSessionByName(labSession.getName()) != null) {
            model.addAttribute("errorMsg", "Integrity violation!");
            return getLabSessions(model);
        } else if(labSessionService.getLebSessionBySessionLeader(labSession.getSessionLeader()) != null)
        {
            model.addAttribute("errorMsg", "Tutor already teaches a lab session!");
            return getLabSessions(model);
        } else if (result.hasErrors()) {
            model.addAttribute("labSessions", labSessionService.getLabSessions());
            return getLabSessions(model);
        }
        labSessionService.createLabSession(labSession);
        return "redirect:/uclan/labSessions";
    }

    @PostMapping("/uclan/labSessions/{id}/delete")
    public String deleteLabSession(@PathVariable("id") long id) {
        try {
            labSessionService.delete(id);
        } catch (Exception ex) {
            return "show-tutors";
        }
        return "redirect:/uclan/labSessions";
    }

    @GetMapping("/uclan/labSessions/{id}/update")
    public String editUpdateForm(@PathVariable Long id, Model model) {
        LabSession labSession = labSessionService.getLabSessionById(id);
        model.addAttribute("updateLabSession", labSessionService.getLabSessionById(id));
        model.addAttribute("tutors", tutorService.getTutors());
        model.addAttribute("modules", moduleService.getModules());
        model.addAttribute("labSessions", labSessionService.getLabSessions());
        return "show-labSessions";
    }

    @PostMapping("/uclan/labSessions/{id}/update")
    public String updateLabSession(@PathVariable Long id, @ModelAttribute("updateLabSession") @Valid LabSession labSession, BindingResult result, Model model) {
        if (result.hasErrors())
            return "show-labSessions";
        labSessionService.update(id, labSession);
        return "redirect:/uclan/labSessions";
    }
}