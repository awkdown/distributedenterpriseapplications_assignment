package com.uclan.controller;

import com.uclan.domain.Module;
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

        if (moduleService.getModuleByEmail(module.getName()) != null) {
            model.addAttribute("errorMsg", "Integrity violation!");
            return getModules(model);
        } else if(moduleService.getModuleByTutor(module.getModuleLeader()) != null)
        {
            model.addAttribute("errorMsg", "Tutor already teaches a module!");
            return getModules(model);
        } else if (result.hasErrors()) {
            model.addAttribute("modules", moduleService.getModules());
            return getModules(model);
        }
        moduleService.createModule(module);
        return "redirect:/uclan/modules";
    }

    @PostMapping("/uclan/modules/{id}/delete")
    public String deleteModule(@PathVariable("id") long id) {
        try {
            moduleService.delete(id);
        } catch (Exception ex) {
            return "show-modules";
        }
        return "redirect:/uclan/modules";
    }

    @GetMapping("/uclan/modules/{id}/update")
    public String editUpdateForm(@PathVariable Long id, Model model) {
        Module module = moduleService.getModuleById(id);
        model.addAttribute("updateModule", moduleService.getModuleById(id));
        model.addAttribute("tutors", tutorService.getTutors());
        model.addAttribute("modules", moduleService.getModules());
        return "show-modules";
    }

    @PostMapping("/uclan/modules/{id}/update")
    public String updateModule(@PathVariable Long id, @ModelAttribute("updateModule") @Valid Module module, BindingResult result, Model model) {
        if (result.hasErrors())
            return "show-modules";
        moduleService.update(id, module);
        return "redirect:/uclan/modules";
    }
}