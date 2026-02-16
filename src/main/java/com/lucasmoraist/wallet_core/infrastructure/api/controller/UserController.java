package com.lucasmoraist.wallet_core.infrastructure.api.controller;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import com.lucasmoraist.wallet_core.application.usecases.user.CreateUserCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByEmailCase;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes.UserDocumentationRoutes;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.user.CreateUserRequest;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.user.UserResponseById;
import com.lucasmoraist.wallet_core.infrastructure.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserDocumentationRoutes {

    private final SecurityGateway securityGateway;
    private final CreateUserCase createUserCase;
    private final GetUserByEmailCase getUserByEmailCase;

    @Override
    @PostMapping("/register")
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

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserResponseById> getUserById() {
        String userEmail = securityGateway.getUserEmailFromAuthentication();
        User user = this.getUserByEmailCase.execute(userEmail);
        UserResponseById response = UserMapper.toResponseById(user);
        return ResponseEntity.ok().body(response);
    }

}
