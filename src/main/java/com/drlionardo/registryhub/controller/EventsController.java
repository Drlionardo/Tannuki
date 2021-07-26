package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Comment;
import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.EventPost;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.service.EventPostService;
import com.drlionardo.registryhub.service.EventService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class EventsController {
    private final EventService eventService;
    private final EventPostService postService;

    public EventsController(EventService eventService, EventPostService postService) {
        this.eventService = eventService;
        this.postService = postService;
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
        model.addAttribute("postList", event.getPosts());
        model.addAttribute("user", user);
        model.addAttribute("isRegistered",eventService.isUserRegistered(user, event));
        return "eventPage";
    }
    @PostMapping("event/{eventId}/post/{postId}/addComment")
    public String addCommentToEventPost(@PathVariable Long eventId,
                                        @PathVariable Long postId,
                                        @RequestParam String commentText,
                                        @AuthenticationPrincipal User user) {
        EventPost eventPost = eventService.findEventPostById(postId);
        postService.addComment(user, eventPost, commentText);
        return "redirect:/event/{eventId}";
    }

}
