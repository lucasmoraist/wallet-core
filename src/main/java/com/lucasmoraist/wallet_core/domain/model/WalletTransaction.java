package com.lucasmoraist.wallet_core.domain.model;

import com.lucasmoraist.wallet_core.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletTransaction(
        UUID id,
        Wallet wallet,
        BigDecimal amount,
        PaymentType type,
        LocalDateTime createdAt
) {

}
