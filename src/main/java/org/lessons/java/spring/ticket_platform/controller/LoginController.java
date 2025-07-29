package org.lessons.java.spring.ticket_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // <--- import corretto
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login") 
    public String loginPage(@RequestParam(name = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("message", "Successful login.");
        }
        return "pages/login";
    }
}
