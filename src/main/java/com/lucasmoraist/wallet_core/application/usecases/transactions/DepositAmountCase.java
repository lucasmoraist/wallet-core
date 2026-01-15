package com.lucasmoraist.wallet_core.application.usecases.transactions;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositAmountCase {

    private final WalletPersistence walletPersistence;
    private final TransactionalPersistence transactionalPersistence;

    public DepositAmountCase(WalletPersistence walletPersistence, TransactionalPersistence transactionalPersistence) {
        this.walletPersistence = walletPersistence;
        this.transactionalPersistence = transactionalPersistence;
    }

    public WalletTransaction execute(UUID walletId, BigDecimal amount) {
        Wallet wallet = walletPersistence.updateAmount(walletId, amount);
        return transactionalPersistence.saveTransaction(wallet, amount);
    }

}
