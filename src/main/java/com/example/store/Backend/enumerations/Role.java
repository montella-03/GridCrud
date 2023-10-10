package com.example.store.Backend.enumerations;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum Role {
    MANAGER,

    USER,
    ADMIN;

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return List.of(() -> "ROLE_" + this.name());

    }
}
