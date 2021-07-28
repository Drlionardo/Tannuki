package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.service.EventPostService;
import com.drlionardo.registryhub.service.EventService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/editor")
@Controller
public class EditorController {
    private final EventService eventService;
    private final EventPostService postService;

    public EditorController(EventService eventService, EventPostService postService) {
        this.eventService = eventService;
        this.postService = postService;
    }

    @GetMapping("/main")
    public String getCreatorPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("creatorEvents", eventService.getEventsByCreator(user));
        return "editorPage";
    }
    @PostMapping("/add")
    public String addEvent(@AuthenticationPrincipal User admin,
                           @RequestParam String name,
                           @RequestParam String description) {
        eventService.addEvent(admin, name, description);
        return "redirect:/editor/main";
    }

    @GetMapping("/event/{id}/edit")
    public String eventPageEditor(@PathVariable Long id, @AuthenticationPrincipal User user,
                                  Model model) {
        Event selectedEvent = eventService.findById(id);
        List<Event> creatorEvents = eventService.getEventsByCreator(user);
        creatorEvents.remove(selectedEvent);
        model.addAttribute("creatorEvents", creatorEvents);
        model.addAttribute("selectedEvent", selectedEvent);
        model.addAttribute("user", user);
        Event event = eventService.findById(id);
        model.addAttribute("requestList", event.getRegistrationRequestList());
        return "eventEditor";
    }

    @PostMapping("/event/{id}/edit")
    public String editEvent(@PathVariable Long id, String eventName, String eventDescription) {
            eventService.updateEvent(id, eventName, eventDescription);
        return "redirect:/editor/event/{id}/edit";
    }
    @PostMapping("/event/{id}/deleteAdmin")
    public String deleteAdminFromEvent(@PathVariable Long id, @RequestParam Long adminId) {
        eventService.removeAdminFromEvent(id, adminId);
        return "redirect:/editor/event/{id}/edit";
    }
    @PostMapping("/event/{id}/addAdmin")
    public String addAdminToEvent(@PathVariable Long id, @RequestParam String adminEmail) {
        eventService.addAdminToEvent(id, adminEmail);
        return "redirect:/editor/event/{id}/edit";
    }

    @PostMapping("/event/{id}/post")
    public String addPostToEvent(@PathVariable(name = "id") Long eventId,
                                 @AuthenticationPrincipal User author,
                                 @RequestParam String title,
                                 @RequestParam String text) {
        Event event = eventService.findById(eventId);
        postService.createPost(event, author, title, text);

        return "redirect:/editor/event/{id}/edit";
    }
}
