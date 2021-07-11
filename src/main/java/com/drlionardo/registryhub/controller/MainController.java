package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.EventRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class MainController {
    private  EventRepo eventRepo;

    public MainController(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @GetMapping
    public String greet() {
        return "about";
    }
    @GetMapping("/main")
    public String allEvents(Model model) {
        model.addAttribute("events", eventRepo.findAll());
        return "main";
    }
    @PostMapping("/main")
        public String addEvent(@AuthenticationPrincipal User admin,
                               @RequestParam String name,
                               @RequestParam String description) {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        var adminList = new ArrayList<User>();
        adminList.add(admin);
        event.setAdmins(adminList);
        eventRepo.save(event);
        return "redirect:/main";
    }
}
