package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;

import java.math.BigDecimal;

public interface TransactionalPersistence {

    WalletTransaction saveTransaction(Wallet wallet, BigDecimal amount, PaymentType paymentType);

}
