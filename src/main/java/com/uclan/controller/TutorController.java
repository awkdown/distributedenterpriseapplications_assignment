package com.uclan.controller;

import com.uclan.domain.Module;
import com.uclan.domain.Tutor;
import com.uclan.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class TutorController {
    private static final String MODEL_TUTOR = "tutors";
    private final TutorService tutorService;
    private final PasswordEncoder encoder;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ModelAttribute("tutor")
    public Tutor getForm() {
        return new Tutor();
    }

    @GetMapping("/uclan/tutors")
    public String getTutors(Model model) {
        model.addAttribute(MODEL_TUTOR, tutorService.getTutors());
        return "show-tutors";
    }

    @PostMapping("/uclan/tutors/add")
    public String insertTutor(@ModelAttribute @Valid Tutor tutor, BindingResult result, Model model) {
        Tutor isAlreadyInDb = tutorService.getTutorByEmail(tutor.getEmail());
        if (isAlreadyInDb != null) {
            model.addAttribute("errorMsg", "Integrity violation!");
            return getTutors(model);
        } else if (result.hasErrors()) {
            model.addAttribute("tutors", tutorService.getTutors());
            return getTutors(model);
        }
        tutorService.createTutor(tutor);
        return "redirect:/uclan/tutors";
    }

    @PostMapping("/uclan/tutors/{id}/delete")
    public String deleteTutor(@PathVariable("id") long id) {
        try {
            tutorService.delete(id);
        } catch (Exception ex) {
            return "show-tutors";
        }
        return "redirect:/uclan/tutors";
    }

    @GetMapping("/uclan/tutors/{id}/update")
    public String editUpdateForm(@PathVariable Long id, Model model) {
        Tutor tutor = tutorService.getTutorById(id);
        //tutor.setPassword(encoder.de);
        model.addAttribute("updateTutor", tutorService.getTutorById(id));
        model.addAttribute("tutors", tutorService.getTutors());
        return "show-tutors";
    }

    @PostMapping("/uclan/tutors/{id}/update")
    public String updateTutor(@PathVariable Long id, @ModelAttribute("updateTutor") @Valid Tutor tutor, BindingResult result, Model model) {
        if (result.hasErrors())
            return "show-tutors";
        tutorService.update(id, tutor);
        return "redirect:/uclan/tutors";
    }
}