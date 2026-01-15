package com.lucasmoraist.wallet_core.domain.message;

import java.util.UUID;

public record Payee(
        UUID payeeId,
        String name,
        String email
) {

}
