package com.drlionardo.registryhub.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static com.drlionardo.registryhub.util.Converter.getDurationFromNow;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User author;
    private String text;
    private LocalDateTime publicationDate;
    @ManyToOne()
    private EventPost post;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public EventPost getPost() {
        return post;
    }

    public void setPost(EventPost post) {
        this.post = post;
    }

    public String getLastUpdateDuration() {
        return getDurationFromNow(publicationDate);
    }
}
