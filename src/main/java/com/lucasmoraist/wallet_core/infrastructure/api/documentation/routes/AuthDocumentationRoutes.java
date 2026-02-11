package com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes;

import com.lucasmoraist.wallet_core.infrastructure.api.dto.auth.AuthenticationRequest;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.auth.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthDocumentationRoutes {

    ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody AuthenticationRequest request);

}
