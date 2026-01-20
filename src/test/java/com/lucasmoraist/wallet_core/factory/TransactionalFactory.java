package com.lucasmoraist.wallet_core.factory;

import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class TransactionalFactory {

    public static WalletTransaction createTransactional(Wallet wallet, BigDecimal amount, PaymentType withdrawal) {
        return new WalletTransaction(
                UUID.randomUUID(),
                wallet,
                amount,
                withdrawal,
                LocalDateTime.now()
        );
    }

}
