package com.lucasmoraist.wallet_core.application.usecases.wallet;

import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.Wallet;

import java.util.UUID;

public class ConsultBalanceCase {

    private final WalletPersistence walletPersistence;

    public ConsultBalanceCase(WalletPersistence walletPersistence) {
        this.walletPersistence = walletPersistence;
    }

    public Wallet execute(UUID walletId) {
        return this.walletPersistence.findById(walletId);
    }

}
