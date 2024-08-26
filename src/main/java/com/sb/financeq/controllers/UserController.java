package com.sb.financeq.controllers;

import com.sb.financeq.entities.User;
import com.sb.financeq.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());

        return "login/register";
    }

    @PostMapping("/register/new")
    public String register(@Valid User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

}
