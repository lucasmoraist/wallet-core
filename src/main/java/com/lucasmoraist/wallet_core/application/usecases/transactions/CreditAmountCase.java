package com.lucasmoraist.wallet_core.application.usecases.transactions;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class CreditAmountCase {

    private final WalletPersistence walletPersistence;
    private final TransactionalPersistence transactionalPersistence;

    public CreditAmountCase(WalletPersistence walletPersistence, TransactionalPersistence transactionalPersistence) {
        this.walletPersistence = walletPersistence;
        this.transactionalPersistence = transactionalPersistence;
    }

    public WalletTransaction execute(UUID walletId, BigDecimal amount, Integer installments) {
        BigDecimal installmentAmount = amount.divide(BigDecimal.valueOf(installments), 8, RoundingMode.HALF_UP);
        WalletTransaction lastTx = null;
        for (int i = 0; i < installments; i++) {
            walletPersistence.updateAmount(walletId, installmentAmount, PaymentType.CREDIT);
            lastTx = transactionalPersistence.saveTransaction(
                    walletPersistence.findById(walletId),
                    installmentAmount,
                    PaymentType.CREDIT
            );
        }
        return lastTx;
    }

}
