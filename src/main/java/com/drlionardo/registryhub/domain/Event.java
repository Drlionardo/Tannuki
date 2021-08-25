package com.drlionardo.registryhub.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.drlionardo.registryhub.util.Converter.getDurationFromNow;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime registrationStartDate;
    private LocalDateTime registrationEndDate;
    private LocalDateTime lastUpdateDate;
    private boolean archived;

    @ManyToMany
    private List<User> admins;
    @OneToMany(mappedBy = "event")
    private Set<RegistrationRequest> registrationRequestList;
    @OneToMany(mappedBy = "event")
    private List<EventPost> posts;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(LocalDateTime registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public LocalDateTime getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(LocalDateTime registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
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

    public Set<RegistrationRequest> getRegistrationRequestList() {
        return registrationRequestList;
    }

    public void setRegistrationRequestList(Set<RegistrationRequest> registrationRequestList) {
        this.registrationRequestList = registrationRequestList;
    }

    public List<EventPost> getPosts() {
        return posts;
    }

    public void setPosts(List<EventPost> posts) {
        this.posts = posts;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateDuration() {
        return getDurationFromNow(lastUpdateDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
