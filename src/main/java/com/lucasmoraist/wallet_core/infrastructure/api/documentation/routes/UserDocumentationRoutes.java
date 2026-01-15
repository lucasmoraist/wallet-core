package com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes;

import com.lucasmoraist.wallet_core.infrastructure.api.dto.user.UserResponseById;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.user.CreateUserRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserDocumentationRoutes {

    ResponseEntity<Void> createUser(CreateUserRequest createUserRequest);
    ResponseEntity<UserResponseById> getUserById(UUID userId);

}
