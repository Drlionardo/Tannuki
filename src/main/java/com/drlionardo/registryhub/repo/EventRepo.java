package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);
}
