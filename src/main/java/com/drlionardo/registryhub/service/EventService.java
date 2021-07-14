package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.RegistrationRequest;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.EventRepo;
import com.drlionardo.registryhub.repo.RegistrationRequestRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private EventRepo eventRepo;
    private UserService userService;
    private RegistrationRequestRepo requestRepo;

    public EventService(EventRepo eventRepo, UserService userService, RegistrationRequestRepo requestRepo) {
        this.eventRepo = eventRepo;
        this.userService = userService;
        this.requestRepo = requestRepo;
    }

    public List<Event> findAll() {
        return eventRepo.findAll();
    }


    public void addEvent(User admin, String name, String description) {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        var adminList = new ArrayList<User>();
        adminList.add(admin);
        event.setAdmins(adminList);
        eventRepo.save(event);
    }

    public Event findById(Long id) {
        return eventRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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

    public List<User> getRegisteredUsers(Event event) {
        return event.getRegistrationRequestList().stream().map(RegistrationRequest::getOwner)
                .collect(Collectors.toList());
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

    public void deleteAdminFromEvent(Long eventId, Long adminId) {
        Event eventFromDb = eventRepo.getById(eventId);
        User removedAdmin = userService.findUserById(adminId);
        eventFromDb.getAdmins().remove(removedAdmin);
        eventRepo.save(eventFromDb);
    }
    public void addAdminToEvent(Long eventId, String adminEmail) {
        Event eventFromDb = eventRepo.getById(eventId);
        User addedAdmin = userService.findUserByEmail(adminEmail);
        eventFromDb.getAdmins().add(addedAdmin);
        eventRepo.save(eventFromDb);

    }

    public void updateEvent(Long eventId, String eventName, String eventDescription) {
        Event eventFromDb = eventRepo.getById(eventId);
        eventFromDb.setName(eventName);
        eventFromDb.setDescription(eventDescription);
        eventRepo.save(eventFromDb);
    }
}
