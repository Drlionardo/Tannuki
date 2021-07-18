package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.exceptions.EmailAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.UsernameAlreadyExistsException;
import com.drlionardo.registryhub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegisterPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        try {
            userService.registerUser(user);
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("responseMessage","User with this email already exists!");
            return "registration";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("responseMessage","User with this username already exists!");
            return "registration";
        }
        return "redirect:/login";
    }
    
    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model) {
        if(userService.activateUser(code)) {
            model.addAttribute("responseMessage", "Your account has been activated!");
        }
        else {
            model.addAttribute("responseMessage", "Error! Unable to activate account!");
        }
        return "login";
    }
}
