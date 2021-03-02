package com.uclan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "index", "home", "/uclan"})
    public String index() {
        return "redirect:/uclan/tutors";
    }
}
