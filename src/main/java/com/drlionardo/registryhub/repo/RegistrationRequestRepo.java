package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.RegistrationRequest;
import com.drlionardo.registryhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRequestRepo extends JpaRepository<RegistrationRequest, Long> {
    Optional<RegistrationRequest> findByOwnerAndEvent(User owner, Event event);
}
