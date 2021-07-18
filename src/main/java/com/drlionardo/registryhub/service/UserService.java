package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.Role;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;
    @Value("${app.url}")
    private String appUrl;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public boolean registerUser(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        if(userFromDb != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        sendActivationCodeToEmail(user);
        return true;
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

    public boolean activateUser(String code) {
        User user = userRepo.findUserByActivationCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(user.getActivationCode().equals(code)) {
            user.setActive(true);
            user.setActivationCode(null);
            userRepo.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    private void sendActivationCodeToEmail(User user) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Registry-Hub. Please, visit: " + appUrl + "/activate/%s" +
                        " to activate your account.",
                user.getUsername(),
                user.getActivationCode()
        );
        mailSender.sendMessage(user.getEmail(), "Activation code", message);
    }
}
