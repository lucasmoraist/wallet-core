package com.lucasmoraist.wallet_core.infrastructure.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lucasmoraist.wallet_core.domain.exception.JwtException;
import com.lucasmoraist.wallet_core.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;

    String secretKey = "test-secret-key";
    int expirationHours = 2;
    String issuer = "payflow";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secretKey", secretKey);
        ReflectionTestUtils.setField(tokenService, "expirationHours", expirationHours);
    }

    @Test
    @DisplayName("Should generate a valid token with subject and role claim")
    void case01() {
        User user = mock(User.class, RETURNS_DEEP_STUBS);
        when(user.email()).thenReturn("test@example.com");
        when(user.role().name()).thenReturn("ADMIN");

        String token = tokenService.generateToken(user);
        assertNotNull(token);

        String subject = tokenService.getSubject(token);
        assertEquals("test@example.com", subject);

        String roleClaim = tokenService.getClaim(token, "role");
        assertEquals("ADMIN", roleClaim);
    }

    @Test
    @DisplayName("Should return correct subject and claim values from a manually built token")
    void case02() {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(Date.from(Instant.now()))
                .withSubject("manual@example.com")
                .withClaim("role", "USER")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(algorithm);

        assertEquals("manual@example.com", tokenService.getSubject(token));
        assertEquals("USER", tokenService.getClaim(token, "role"));
    }

    @Test
    @DisplayName("Should throw JwtException when verifying an invalid token")
    void case03() {
        String badToken = "this.is.not.a.valid.token";
        assertThrows(JwtException.class, () -> tokenService.getSubject(badToken));
    }

    @Test
    @DisplayName("Should throw JwtException when verifying an expired token")
    void case04() {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String expiredToken = JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(Date.from(Instant.now().minusSeconds(7200)))
                .withSubject("expired@example.com")
                .withClaim("role", "USER")
                .withExpiresAt(Date.from(Instant.now().minusSeconds(3600)))
                .sign(algorithm);

        assertThrows(JwtException.class, () -> tokenService.getClaim(expiredToken, "role"));
    }

}