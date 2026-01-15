package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletPersistence {

    Wallet findById(UUID walletId);
    Wallet createWalletForUser(User user);
    Wallet updateAmount(UUID walletId, BigDecimal amount, PaymentType paymentType);

}
