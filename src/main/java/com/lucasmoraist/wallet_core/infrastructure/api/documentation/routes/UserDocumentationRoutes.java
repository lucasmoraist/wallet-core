package com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes;

import com.lucasmoraist.wallet_core.infrastructure.api.dto.user.CreateUserRequest;
import org.springframework.http.ResponseEntity;

public interface UserDocumentationRoutes {

    ResponseEntity<Void> createUser(CreateUserRequest createUserRequest);

}
