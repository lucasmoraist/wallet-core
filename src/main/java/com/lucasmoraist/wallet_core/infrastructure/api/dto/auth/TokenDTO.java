package com.lucasmoraist.wallet_core.infrastructure.api.dto.auth;

public record TokenDTO(
        String token,
        String type,
        Integer expiresIn
) {

}
