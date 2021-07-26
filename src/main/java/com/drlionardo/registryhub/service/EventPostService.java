package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.Comment;
import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.EventPost;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.CommentRepo;
import com.drlionardo.registryhub.repo.EventPostRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class EventPostService {
    private final EventPostRepo eventPostRepo;
    private final CommentRepo commentRepo;

    public EventPostService(EventPostRepo eventPostRepo, CommentRepo commentRepo) {
        this.eventPostRepo = eventPostRepo;
        this.commentRepo = commentRepo;
    }

    public EventPost createPost(Event event, User author, String title, String text) {
        EventPost post = new EventPost();
        post.setEvent(event);
        post.setAuthor(author);
        post.setTitle(title);
        post.setText(text);
        post.setPublicationDate(LocalDateTime.now());

        eventPostRepo.save(post);
        return post;
    }

    public Comment addComment(User user, EventPost post, String text) {
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setPost(post);
        comment.setText(text);
        comment.setPublicationDate(LocalDateTime.now());

        commentRepo.save(comment);
        return comment;
    }
}
