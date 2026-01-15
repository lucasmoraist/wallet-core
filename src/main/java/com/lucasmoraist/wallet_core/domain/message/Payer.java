package com.lucasmoraist.wallet_core.domain.message;

import java.util.UUID;

public record Payer(
        UUID payerId,
        String name,
        String email
) {

}
