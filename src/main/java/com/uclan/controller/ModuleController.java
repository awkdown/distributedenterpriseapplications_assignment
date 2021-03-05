package com.uclan.controller;

import com.uclan.service.ModuleService;
import com.uclan.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.logging.Logger;

@Controller
public class ModuleController {
    private static final String MODEL_Module = "modules";
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
}
