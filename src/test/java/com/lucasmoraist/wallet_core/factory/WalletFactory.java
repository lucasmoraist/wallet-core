package com.lucasmoraist.wallet_core.factory;

import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public final class WalletFactory {

    public static Wallet createWallet(User user) {
        return new Wallet(
                UUID.randomUUID(),
                user,
                BigDecimal.valueOf(1000L),
                0,
                null
        );
    }

    public static Wallet createWallet(UUID walletId) {
        return new Wallet(
                walletId,
                UserFactory.createUser(),
                BigDecimal.valueOf(1000L),
                0,
                null
        );
    }

}
