package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Long, User> {
}
