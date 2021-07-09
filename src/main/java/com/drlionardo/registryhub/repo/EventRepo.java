package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Long, Event> {
}
