package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Role;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.exceptions.EmailAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.UsernameAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.WrongPasswordException;
import com.drlionardo.registryhub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


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
            model.addAttribute("responseMessage", "User with this email already exists!");
            return "registration";
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("responseMessage", "User with this username already exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @PostMapping("/profile/{id}/repeatEmailActivation")
    public String repeatActivationEmailMessage(@PathVariable Long id, Model model) {
        userService.repeatActivationEmailMessage(id);
        model.addAttribute("responseMessage", "Email with activation was sent again");
        model.addAttribute("user", userService.findUserById(id));
        return "userProfile";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model) {
        if (userService.activateUser(code)) {
            model.addAttribute("responseMessage", "Your account has been activated!");
        } else {
            model.addAttribute("responseMessage", "Error! Unable to activate account!");
        }
        return "login";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", userService.findUserById(id));
        return "userProfile";
    }

    @PostMapping("/profile/{id}/changeEmail")
    public String changeEmail(@PathVariable Long id, String newEmail, Model model) {
        userService.changeEmailWithValidation(id, newEmail);
        model.addAttribute("user",userService.findUserById(id));
        return "userProfile";
    }

    @PostMapping("/profile/{id}/changePassword")
    public String changePassword(@PathVariable Long id, String oldPassword, String newPassword, Model model) {
        try {
            userService.changePassword(id, oldPassword, newPassword);
            model.addAttribute("responseMessage", "Password has been changed");
        } catch (WrongPasswordException e) {
            model.addAttribute("responseMessage", "Incorrect old password, please try again");
        }
        model.addAttribute("user",userService.findUserById(id));

        return "userProfile";
    }

    @PostMapping("profile/{id}/updateRoles")
    public String updateRoles(@PathVariable Long id, @RequestParam Map<String, String> form, Model model) {
        userService.updateRoles(id, form);
        return "redirect:/profile/{id}";
    }
}
