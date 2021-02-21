package controller;

import domain.Tutor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.TutorService;

import javax.validation.Valid;

@RequiredArgsConstructor

@Controller
public class TutorController {
    private static final String MODEL_TUTOR = "tutors";
    private final TutorService tutorService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/tutors")
    public String getIngredients(Model model) {
        model.addAttribute(MODEL_TUTOR, tutorService.getTutors());
        return "show/show-ingredients";
    }

    @PostMapping("/tutors/add")
    public String insertTutor(@ModelAttribute @Valid Tutor tutor, BindingResult result) {
        if (result.hasErrors())
            return "show/show-tutors";
        tutorService.createTutor(tutor);
        return "redirect:tutors";
    }
}
