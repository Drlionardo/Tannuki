package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPostRepo extends JpaRepository<EventPost, Long> {
}
