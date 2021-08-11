package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.exceptions.EmailAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.UsernameAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.WrongPasswordException;
import com.drlionardo.registryhub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class AuthorizationController {
    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("login")
    public String login() {
        return "authorization/login";
    }
    @RequestMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "authorization/login";
    }

    @GetMapping("/registration")
    public String getRegisterPage() {
        return "authorization/registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        try {
            userService.registerUser(user);
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("errorMessage", "User with this email already exists!");
            model.addAttribute("registrationError", true);
            return "authorization/registration";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("registrationError", true);
            model.addAttribute("errorMessage", "User with this username already exists!");
            return "authorization/registration";
        }
        return "redirect:/authorization/login";
    }
}
