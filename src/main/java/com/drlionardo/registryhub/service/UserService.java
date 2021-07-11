package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}