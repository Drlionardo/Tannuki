package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.EventPost;
import com.drlionardo.registryhub.domain.RegistrationRequest;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.EventPostRepo;
import com.drlionardo.registryhub.repo.EventRepo;
import com.drlionardo.registryhub.repo.RegistrationRequestRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepo eventRepo;
    private final EventPostRepo postRepo;
    private final UserService userService;
    private final RegistrationRequestRepo requestRepo;

    public EventService(EventRepo eventRepo, EventPostRepo postRepo, UserService userService, RegistrationRequestRepo requestRepo) {
        this.eventRepo = eventRepo;
        this.postRepo = postRepo;
        this.userService = userService;
        this.requestRepo = requestRepo;
    }

    public Page<Event> findAll(Pageable pageable) {
        return eventRepo.findAll(pageable);
    }
    public Event findById(Long id) {
        return eventRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void addEvent(User admin, String name, String description) {
        Event event = new Event();
        event.setTitle(name);
        event.setDescription(description);
        event.setCreationDate(LocalDateTime.now());
        var adminList = new ArrayList<User>();
        adminList.add(admin);
        event.setAdmins(adminList);
        eventRepo.save(event);
    }

    public void registerUserForEvent(User user, Event event) {
        RegistrationRequest request = new RegistrationRequest();
        request.setOwner(user);
        request.setEvent(event);
        request.setRegistrationTime(LocalDateTime.now());
        requestRepo.save(request);
    }
    @Transactional
    public Boolean isUserRegistered(User user, Event event) {
        return findUserRequestForEvent(user, event) != null;
    }

    public void cancelRegistration(User usr, Event event) {
        User user = (User) userService.loadUserByUsername(usr.getEmail());
        RegistrationRequest requestFromDb = user.getRequestList().stream()
                .filter((registrationRequest -> registrationRequest.getEvent().equals(event))).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        requestRepo.delete(requestFromDb);
    }

    private RegistrationRequest findUserRequestForEvent(User usr, Event event) {
        User user = (User) userService.loadUserByUsername(usr.getEmail());
        return user.getRequestList().stream()
                .filter((registrationRequest -> registrationRequest.getEvent().equals(event)))
                .findFirst().orElse(null);
    }

    public void removeAdminFromEvent(Long eventId, Long adminId) {
        Event eventFromDb = findById(eventId);
        User removedAdmin = userService.findUserById(adminId);
        eventFromDb.getAdmins().remove(removedAdmin);
        eventRepo.save(eventFromDb);
    }
    public void addAdminToEvent(Long eventId, String adminEmail) {
        Event eventFromDb = findById(eventId);
        User addedAdmin = userService.findUserByEmail(adminEmail);
        eventFromDb.getAdmins().add(addedAdmin);
        eventRepo.save(eventFromDb);
    }

    public void updateEvent(Long eventId, String eventName, String eventDescription) {
        Event eventFromDb = findById(eventId);
        eventFromDb.setTitle(eventName);
        eventFromDb.setDescription(eventDescription);
        eventRepo.save(eventFromDb);
    }

    public Page<Event> getEventsByCreator(User user, Pageable pageable) {
        return eventRepo.findAllByAdminsContains(user, pageable);
    }

    public EventPost findEventPostById(Long postId) {
        return postRepo.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Page<Event> getRegisteredEventsByUserId(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);
        return  eventRepo.findByRegistrationRequestList_Owner(user, pageable);
    }
}
