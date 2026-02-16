package com.lucasmoraist.wallet_core.infrastructure.api.dto.user;

import java.util.Map;
import java.util.UUID;

public record UserResponseById(
        UUID id,
        String name,
        String email,
        String document,
        Map<String, Object> wallet
) {

}
