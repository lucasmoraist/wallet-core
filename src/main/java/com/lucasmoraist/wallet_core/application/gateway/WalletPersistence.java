package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;

import java.util.UUID;

public interface WalletPersistence {

    Wallet findById(UUID walletId);
    Wallet createWalletForUser(User user);

}
