package com.drlionardo.registryhub.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @OneToMany
    private List<RegistrationRequest> requestList;
}
