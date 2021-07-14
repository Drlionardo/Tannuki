package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userFromDb = userRepo.findByEmail(email);
        if(userFromDb == null) {
            throw new UsernameNotFoundException(email);
        }
        else return userFromDb;
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
