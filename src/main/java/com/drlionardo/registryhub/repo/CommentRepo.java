package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
