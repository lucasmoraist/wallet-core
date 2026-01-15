package com.lucasmoraist.wallet_core.application.usecases.transactions;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;

import java.math.BigDecimal;
import java.util.UUID;

public class WithdrawAmountCase {

    private final WalletPersistence walletPersistence;
    private final TransactionalPersistence transactionalPersistence;

    public WithdrawAmountCase(WalletPersistence walletPersistence, TransactionalPersistence transactionalPersistence) {
        this.walletPersistence = walletPersistence;
        this.transactionalPersistence = transactionalPersistence;
    }

    public WalletTransaction execute(UUID walletId, BigDecimal amount) {
        Wallet wallet = walletPersistence.updateAmount(walletId, amount, PaymentType.WITHDRAWAL);
        return transactionalPersistence.saveTransaction(wallet, amount, PaymentType.WITHDRAWAL);
    }

}
