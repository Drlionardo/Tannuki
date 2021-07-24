package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);
    List<Event> findAllByAdminsContains(User user);
}
