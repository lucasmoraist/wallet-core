package com.lucasmoraist.wallet_core.infrastructure.api.controller;

import com.lucasmoraist.wallet_core.application.usecases.auth.AuthenticationCase;
import com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes.AuthDocumentationRoutes;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.auth.AuthenticationRequest;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.auth.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/login")
public class AuthenticationController implements AuthDocumentationRoutes {

    @Value("${spring.security.jwt.expiration-hours}")
    private Integer expirationHours;
    private final AuthenticationCase authenticationCase;

    @Override
    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(AuthenticationRequest request) {
        String token = this.authenticationCase.execute(request.email(), request.password());
        TokenDTO response = new TokenDTO(token, "Bearer", expirationHours);
        return ResponseEntity.ok().body(response);
    }

}
