package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.Image;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.service.EventPostService;
import com.drlionardo.registryhub.service.EventService;
import com.drlionardo.registryhub.service.ImageStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class EditorController {
    private final EventService eventService;
    private final EventPostService postService;
    private final ImageStorageService imageService;

    public EditorController(EventService eventService, EventPostService postService, ImageStorageService imageService) {
        this.eventService = eventService;
        this.postService = postService;
        this.imageService = imageService;
    }

    @GetMapping("/editor")
    public String getCreatorPage(@AuthenticationPrincipal User user,
                                 @PageableDefault(size = 5, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model) {
        model.addAttribute("creatorEvents", eventService.getEventsByCreator(user, pageable));
        return "editorPage";
    }
    @PostMapping("editor/addEvent")
    public String addEvent(@AuthenticationPrincipal User admin,
                           @RequestParam(value = "eventLogo", required = false) MultipartFile multipartFile,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                           RedirectAttributes redirectAttributes) {
        eventService.addEvent(admin, title, description, endDate, multipartFile);

        redirectAttributes.addFlashAttribute("successMessage", "New event created");
        return "redirect:/editor";
    }

    @GetMapping("/editor/edit")
    public String eventPageEditor(@PageableDefault(size = 5, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam Long id, @AuthenticationPrincipal User user, Model model) {
        Event selectedEvent = eventService.findById(id);
        Page<Event> creatorEvents = eventService.getEventsByCreator(user, pageable);
        model.addAttribute("creatorEvents", creatorEvents);
        model.addAttribute("selectedEvent", selectedEvent);
        Event event = eventService.findById(id);
        model.addAttribute("requestList", event.getRegistrationRequestList());
        return "eventEditor";
    }

    @PostMapping("/editor/event/edit")
    public String editEvent(@RequestParam Long id, String eventTitle, String eventDescription,
                            RedirectAttributes redirectAttributes) {
        eventService.updateEvent(id, eventTitle, eventDescription);
        redirectAttributes.addFlashAttribute("successMessage", "Event updated");
        redirectAttributes.addAttribute("id", id);
        return "redirect:/editor/edit";
    }
    @PostMapping("/editor/event/deleteAdmin")
    public String deleteAdminFromEvent(@RequestParam Long id, @RequestParam Long adminId,
                                       RedirectAttributes redirectAttributes) {
        eventService.removeAdminFromEvent(id, adminId);
        redirectAttributes.addFlashAttribute("successMessage", "Admin has been removed");
        redirectAttributes.addAttribute("id", id);
        return "redirect:/editor/edit";
    }
    @PostMapping("/editor/event/addAdmin")
    public String addAdminToEvent(@RequestParam Long id, @RequestParam String adminEmail,
                                  RedirectAttributes redirectAttributes) {
        eventService.addAdminToEvent(id, adminEmail);
        redirectAttributes.addFlashAttribute("successMessage", "New admin email added");
        redirectAttributes.addAttribute("id", id);
        return "redirect:/editor/edit";
    }

    @PostMapping("/editor/event/addPost")
    public String addPostToEvent(@RequestParam(name = "id") Long eventId,
                                 @AuthenticationPrincipal User author,
                                 @RequestParam String title, @RequestParam String text,
                                 RedirectAttributes redirectAttributes) {
        Event event = eventService.findById(eventId);
        postService.createPost(event, author, title, text);
        redirectAttributes.addFlashAttribute("successMessage", "New post has been added");
        redirectAttributes.addAttribute("id", eventId);
        return "redirect:/editor/edit";
    }
}
