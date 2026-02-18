package com.lucasmoraist.wallet_core.infrastructure.security.service;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserPersistence userPersistence;
    @InjectMocks
    CustomUserDetailsService service;

    @Test
    @DisplayName("Should load user details when user exists")
    void case01() {
        User user = mock(User.class, Answers.RETURNS_DEEP_STUBS);

        when(userPersistence.findByEmail("user@example.com")).thenReturn(user);
        when(user.email()).thenReturn("user@example.com");
        when(user.password()).thenReturn("secret");
        when(user.role().name()).thenReturn("ADMIN");

        UserDetails userDetails = service.loadUserByUsername("user@example.com");

        assertEquals("user@example.com", userDetails.getUsername());
        assertEquals("secret", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN")));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void case02() {
        when(userPersistence.findByEmail("notfound@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("notfound@example.com"));
    }

}