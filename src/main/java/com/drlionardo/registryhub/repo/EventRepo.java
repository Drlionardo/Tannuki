package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepo extends PagingAndSortingRepository<Event, Long> {
    Optional<Event> findById(Long id);
    Page<Event> findAllByAdminsContains(User user, Pageable pageable);
    Page<Event> findByRegistrationRequestList_Owner(User user, Pageable pageable);
}
