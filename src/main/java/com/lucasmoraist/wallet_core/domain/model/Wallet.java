package com.lucasmoraist.wallet_core.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record Wallet(
        UUID id,
        User user,
        BigDecimal balance,
        Integer version,
        List<WalletTransaction> transactions
) {

}
