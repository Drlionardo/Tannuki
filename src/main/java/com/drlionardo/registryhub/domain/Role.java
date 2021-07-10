package com.drlionardo.registryhub.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, CREATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
