package com.lucasmoraist.wallet_core.infrastructure.filter;

import com.lucasmoraist.wallet_core.application.gateway.TokenGateway;
import com.lucasmoraist.wallet_core.infrastructure.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    TokenGateway tokenGateway;
    @Mock
    CustomUserDetailsService customUserDetailsService;
    @InjectMocks
    SecurityFilter securityFilter;

    MockHttpServletRequest request;
    MockHttpServletResponse response;
    FilterChain filterChain;

    @BeforeEach
    void setup() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("When no Authorization header is present, authentication should not be set and filter chain should proceed")
    void case01() throws ServletException, IOException {
        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("When token has no subject, authentication should not be set and filter chain should proceed")
    void case02() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer sometoken");
        when(tokenGateway.getSubject("sometoken")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(tokenGateway, times(1)).getSubject("sometoken");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("When user not found for token subject, authentication should not be set and filter chain should proceed")
    void case03() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer tok");
        when(tokenGateway.getSubject("tok")).thenReturn("unknownUser");
        when(customUserDetailsService.loadUserByUsername("unknownUser"))
                .thenThrow(new UsernameNotFoundException("not found"));

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(customUserDetailsService, times(1)).loadUserByUsername("unknownUser");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("When valid token is provided, authentication should be set and filter chain should proceed")
    void case04() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer validToken");
        when(tokenGateway.getSubject("validToken")).thenReturn("jdoe");

        UserDetails user = new User("jdoe", "pass", Collections.emptyList());
        when(customUserDetailsService.loadUserByUsername("jdoe")).thenReturn(user);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("jdoe", SecurityContextHolder.getContext().getAuthentication().getName());
        assertEquals(user, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain, times(1)).doFilter(request, response);
    }

}