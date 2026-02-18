package com.lucasmoraist.wallet_core.infrastructure.security.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    SecurityContext securityContext;
    @Mock
    Authentication authentication;
    @InjectMocks
    SecurityService securityService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should delegate password encryption to PasswordEncoder")
    void case01() {
        String raw = "secret";
        String encoded = "encodedSecret";
        when(passwordEncoder.encode(raw)).thenReturn(encoded);

        String result = securityService.encryptPassword(raw);

        assertEquals(encoded, result);
        verify(passwordEncoder).encode(raw);
    }

    @Test
    @DisplayName("Should delegate password verification to PasswordEncoder")
    void case02() {
        String raw = "secret";
        String encrypted = "encodedSecret";
        when(passwordEncoder.matches(raw, encrypted)).thenReturn(true);

        boolean result = securityService.verifyPassword(raw, encrypted);

        assertTrue(result);
        verify(passwordEncoder).matches(raw, encrypted);
    }

    @Test
    @DisplayName("Should return user email from authentication when authenticated")
    void case03() {
        String email = "user@example.com";
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(email);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String result = securityService.getUserEmailFromAuthentication();

        assertEquals(email, result);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when authentication is null")
    void case04() {
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(IllegalArgumentException.class, () -> securityService.getUserEmailFromAuthentication());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when user is not authenticated")
    void case05() {
        when(authentication.isAuthenticated()).thenReturn(false);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(IllegalArgumentException.class, () -> securityService.getUserEmailFromAuthentication());
    }

}