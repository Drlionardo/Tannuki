package com.drlionardo.registryhub.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistrationRequest {
    @Id
    @GeneratedValue
    @Column(name = "registration_request_id")
    private long id;
    //CascadeType.ALL throws org.hibernate.PersistentObjectException
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usr_id")
    private User owner;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")
    private Event event;
    private LocalDateTime registrationTime;

    public RegistrationRequest() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
}
