package com.vanson.EcommerceSaaS.security;

import com.vanson.EcommerceSaaS.entity.Role;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final Role role;

    public JwtAuthenticationToken(String email, Role role) {
        super(Collections.emptyList());
        this.email = email;
        this.role = role;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}