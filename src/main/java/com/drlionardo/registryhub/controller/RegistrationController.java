package com.drlionardo.registryhub.controller;

import com.drlionardo.registryhub.domain.Role;
import com.drlionardo.registryhub.domain.User;
import com.drlionardo.registryhub.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Optional;

@Controller
public class RegistrationController {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String getRegisterPage() {
        return "registration";
    }
    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        Optional<User> userFromDb  = userRepo.findByEmail(user.getEmail());
        if(userFromDb.isPresent()) {
            model.addAttribute("responseMessage","User with this email already exists!");
            return "registration";
        }
        else {
            user.setRoles(Collections.singleton(Role.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        return "redirect:/login";
    }
}
