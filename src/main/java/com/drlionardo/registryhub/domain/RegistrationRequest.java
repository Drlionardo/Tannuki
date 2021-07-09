package com.drlionardo.registryhub.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User owner;
    @ManyToOne
    private Event event;
    private LocalDateTime registrationTime;

}
