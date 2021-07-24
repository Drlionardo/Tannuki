package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Role;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.service.EventService;
import com.drlionardo.registryhub.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class UserController {
    UserService userService;
    EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("registeredEvents", userService.getRegisteredEventsByUserId(user.getId()));
        return "userProfile";
    }
}
