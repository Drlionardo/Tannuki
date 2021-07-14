package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.service.EventService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class EventsController {
    private EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String greet() {
        return "about";
    }

    @GetMapping("/main")
    public String allEvents(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "main";
    }

    @PostMapping("/main")
        public String addEvent(@AuthenticationPrincipal User admin,
                               @RequestParam String name,
                               @RequestParam String description) {
        eventService.addEvent(admin, name, description);
        return "redirect:/main";
    }

    @PostMapping("event/{id}/register")
    public String registerForEvent(@PathVariable Long id, @AuthenticationPrincipal User user,
                                   Model model) {
        eventService.registerUserForEvent(user, eventService.findById(id));
        return "redirect:/event/{id}";
    }

    @PostMapping("event/{id}/cancel")
    public String cancelRegistration(@PathVariable Long id, @AuthenticationPrincipal User user) {
        eventService.cancelRegistration(user, eventService.findById(id));
        return "redirect:/event/{id}";
    }

    @GetMapping("event/{id}")
    public String eventPage(@PathVariable Long id, @AuthenticationPrincipal User user,
                            Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("requestList", event.getRegistrationRequestList());
        model.addAttribute("user", user);
        model.addAttribute("isRegistered",eventService.isUserRegistered(user, event));
        return "eventPage";
    }

    @GetMapping("event/{id}/edit")
    public String eventPageEditor(@PathVariable Long id, @AuthenticationPrincipal User user,
                            Model model) {
        model.addAttribute("event", eventService.findById(id));
        model.addAttribute("user", user);
        return "eventEditor";
    }

    @PostMapping("event/{id}/edit")
    public String editEvent(@PathVariable Long id, String eventName, String eventDescription) {
        eventService.updateEvent(id, eventName, eventDescription);
        return "redirect:/event/{id}";
    }

    @PostMapping("/event/{id}/deleteAdmin")
    public String deleteAdminFromEvent(@PathVariable Long id, Long adminId) {
        eventService.deleteAdminFromEvent(id, adminId);
        return "redirect:/event/{id}";
    }
    @PostMapping("/event/{id}/addAdmin")
    public String addAdminToEvent(@PathVariable Long id, String adminEmail) {
        eventService.addAdminToEvent(id, adminEmail);
        return "redirect:/event/{id}";
    }

}
