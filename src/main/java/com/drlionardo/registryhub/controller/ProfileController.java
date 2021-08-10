package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Role;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.exceptions.WrongPasswordException;
import com.drlionardo.registryhub.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String userProfile(@RequestParam Long id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("profileId", id);
        model.addAttribute("allRoles", Role.values());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("registeredEvents", userService.getRegisteredEventsByUserId(user.getId()));
        return "userProfile";
    }
    @PostMapping("/profile/changeEmail")
    public String changeEmail(@RequestParam Long id, String newEmail, String currentPassword,
                              RedirectAttributes redirectAttributes) {
        if(userService.checkPassword(currentPassword, id)) {
            userService.changeEmailWithValidation(id, newEmail);
            redirectAttributes.addFlashAttribute("successMessage", "Email has been changed");
        }
        else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error! Can't change email");

        }
        redirectAttributes.addAttribute("id", id);
        return "redirect:/profile";
    }

    @PostMapping("/profile/changePassword")
    public String changePassword(@RequestParam Long id, String currentPassword, String newPassword,
                                 RedirectAttributes redirectAttributes, Model model) {
        try {
            userService.changePassword(id, currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password has been changed");
        } catch (WrongPasswordException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect old password, please try again");
        }
        redirectAttributes.addAttribute("id", id);
        return "redirect:/profile";
    }

    @PostMapping("/profile/repeatEmailActivation")
    public String repeatActivationEmailMessage(@RequestParam Long id,
                                               RedirectAttributes redirectAttributes) {
        userService.repeatActivationEmailMessage(id);
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addFlashAttribute("successMessage","Activation code has been sent to email");
        return "redirect:/profile";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model) {
        if (userService.activateUser(code)) {
            model.addAttribute("responseMessage", "Your account has been activated!");
        } else {
            model.addAttribute("responseMessage", "Error! Unable to activate account!");
        }
        return "authorization/emailActivated";
    }

    @PostMapping("profile/updateRoles")
    public String updateRoles(@RequestParam Long id, @RequestParam Map<String, String> form,
                              RedirectAttributes redirectAttributes) {
        userService.updateRoles(id, form);
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addFlashAttribute("successMessage","Roles have been successfully updated");
        return "redirect:/profile";
    }
}
