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
}
