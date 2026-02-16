package com.lucasmoraist.wallet_core.infrastructure.security.service;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityGateway {

    private final PasswordEncoder passwordEncoder;

    public SecurityService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encryptPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encryptedPassword) {
        return this.passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public String getUserEmailFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }
        return authentication.getName();
    }

}
