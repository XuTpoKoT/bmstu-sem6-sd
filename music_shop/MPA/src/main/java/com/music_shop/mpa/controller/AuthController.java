package com.music_shop.mpa.controller;

import com.music_shop.BL.API.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam(name = "username") String login,
                               @RequestParam(name = "password") String password,
                                Model model) {
        try {
            authService.registration(login, password);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/registration";
        }
        return "redirect:/";
    }
}
