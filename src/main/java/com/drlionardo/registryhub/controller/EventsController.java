package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.EventPost;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.service.EventPostService;
import com.drlionardo.registryhub.service.EventService;
import com.drlionardo.registryhub.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EventsController {
    private final EventService eventService;
    private final EventPostService postService;
    private final UserService userService;

    public EventsController(EventService eventService, EventPostService postService, UserService userService) {
        this.eventService = eventService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String welcomePage() {
        return "about";
    }

    @GetMapping("events")
    public String allEvents(@PageableDefault(sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(defaultValue = "all") String filter, Model model) {
        model.addAttribute("events", eventService.findAllWithFilter(pageable, filter));
        return "events";
    }

    @GetMapping("myEvents")
    public String userEvents(@PageableDefault(sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable,
                             @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("events", eventService.getRegisteredEventsByUserId(user.getId(), pageable));
        return "myevent";
    }

    @GetMapping("event")
    public String eventPage(@RequestParam Long id, @AuthenticationPrincipal User user,
                            Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("requestList", event.getRegistrationRequestList());
        model.addAttribute("postList", event.getPosts());
        model.addAttribute("user", user);
        if(user != null) {
            model.addAttribute("isRegistered", eventService.isUserRegistered(user, event));
        }
        return "eventPage";
    }

    @PostMapping("event/register")
    public String registerForEvent(@RequestParam Long id, @AuthenticationPrincipal User user,
                                   RedirectAttributes redirectAttributes) {
        eventService.registerUserForEvent(user, eventService.findById(id));
        redirectAttributes.addAttribute("id", id);
        return "redirect:/event";
    }

    @PostMapping("event/cancel")
    public String cancelRegistration(@RequestParam Long id, @AuthenticationPrincipal User user,
                                     RedirectAttributes redirectAttributes) {
        eventService.cancelRegistration(user, eventService.findById(id));
        redirectAttributes.addAttribute("id", id);
        return "redirect:/event";
    }

    @PostMapping("event/addComment")
    public String addCommentToEventPost(@RequestParam Long eventId,
                                        @RequestParam Long postId,
                                        @RequestParam String commentText,
                                        @AuthenticationPrincipal User user,
                                        RedirectAttributes redirectAttributes) {
        EventPost eventPost = eventService.findEventPostById(postId);
        postService.addComment(user, eventPost, commentText);
        redirectAttributes.addAttribute("id", eventId);
        return "redirect:/event";
    }
}
