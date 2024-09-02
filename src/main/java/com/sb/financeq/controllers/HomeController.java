package com.sb.financeq.controllers;

import com.sb.financeq.entities.User;
import com.sb.financeq.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("user", user);

        return "home/home";
    }
}
