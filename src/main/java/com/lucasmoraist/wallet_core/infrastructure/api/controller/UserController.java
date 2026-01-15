package com.lucasmoraist.wallet_core.infrastructure.api.controller;

import com.lucasmoraist.wallet_core.application.usecases.user.CreateUserCase;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes.UserDocumentationRoutes;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.user.CreateUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserDocumentationRoutes {

    private final CreateUserCase createUserCase;

    @Override
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = this.createUserCase.execute(
                request.name(),
                request.document(),
                request.email(),
                request.password()
        );
        URI location = URI.create(String.format("/api/v1/users/%s", user.id()));
        return ResponseEntity.created(location).build();
    }

}
