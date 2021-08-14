package com.drlionardo.registryhub.service;

import com.drlionardo.registryhub.domain.Event;
import com.drlionardo.registryhub.domain.RegistrationRequest;
import com.drlionardo.registryhub.domain.Role;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.exceptions.EmailAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.UsernameAlreadyExistsException;
import com.drlionardo.registryhub.exceptions.WrongPasswordException;
import com.drlionardo.registryhub.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

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

    public void registerUser(User user) {
        checkIfUserAlreadyExists(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        sendActivationCodeToEmail(user);
    }

    private void checkIfUserAlreadyExists(User user) {
        if(userRepo.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
        if(userRepo.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException();
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findUserByActivationCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(user.getActivationCode().equals(code)) {
            user.setEmailValidated(true);
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

    public void repeatActivationEmailMessage(Long id) {
        User user = findUserById(id);
        sendActivationCodeToEmail(user);
    }

    public void changeEmailWithValidation(Long userId, String newEmail) {
        User user = findUserById(userId);
        user.setEmail(newEmail);
        user.setEmailValidated(false);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        sendActivationCodeToEmail(user);
    }
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findUserById(userId);
        if(passwordEncoder.matches(oldPassword,user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        }
        else throw new WrongPasswordException();
    }

    public void updateRoles(Long id, Map<String, String> form) {
        User user = findUserById(id);
        Set<String> allRoles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        //Add all roles from form map
        for (String key : form.keySet()) {
            if(allRoles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public boolean checkPassword(String currentPassword, Long userId) {
        return passwordEncoder.matches(currentPassword, findUserById(userId).getPassword());
    }
}
