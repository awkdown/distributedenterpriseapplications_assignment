package com.uclan.controller;

import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import com.uclan.service.ModuleService;
import com.uclan.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class ModuleController {
    private static final String MODEL_Module = "modules";
    private static final String MODEL_Tutors = "tutors";
    private final ModuleService moduleService;
    private final TutorService tutorService;

    public ModuleController(ModuleService moduleService, TutorService tutorService) {
        this.moduleService = moduleService;
        this.tutorService = tutorService;
    }

    @ModelAttribute("module")
    public Module getModule() {
        return new Module();
    }

    @GetMapping("/uclan/modules")
    public String getModules(Model model) {
        model.addAttribute(MODEL_Tutors, tutorService.getTutors());
        model.addAttribute(MODEL_Module, moduleService.getModules());
        return "show-modules";
    }

    @PostMapping("/uclan/modules/add")
    public String insertModule(@ModelAttribute @Valid Module module, BindingResult result, Model model) {
        Module isAlreadyInDb = moduleService.getModuleById(module.getName());
        if (isAlreadyInDb != null) {
            model.addAttribute("errorMsg", "Integrity violation!");
            return getModules(model);
        } else if (result.hasErrors()) {
            model.addAttribute("modules", moduleService.getModules());
            return getModules(model);
        }
        moduleService.createModule(module);
        return "redirect:/uclan/modules";
    }
}