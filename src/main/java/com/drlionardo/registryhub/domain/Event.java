package com.drlionardo.registryhub.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;
    private String name;
    private String description;
    @ManyToMany
    private List<User> admins;
    @OneToMany
    private List<RegistrationRequest> registrationRequestList;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(LocalDate registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public LocalDate getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(LocalDate registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public List<RegistrationRequest> getRegistrationRequestList() {
        return registrationRequestList;
    }

    public void setRegistrationRequestList(List<RegistrationRequest> registrationRequestList) {
        this.registrationRequestList = registrationRequestList;
    }
}
