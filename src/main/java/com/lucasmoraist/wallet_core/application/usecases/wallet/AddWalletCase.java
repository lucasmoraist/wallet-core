package com.lucasmoraist.wallet_core.application.usecases.wallet;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;

import java.util.UUID;

public class AddWalletCase {

    private final UserPersistence userPersistence;
    private final WalletPersistence walletPersistence;

    public AddWalletCase(UserPersistence userPersistence, WalletPersistence walletPersistence) {
        this.userPersistence = userPersistence;
        this.walletPersistence = walletPersistence;
    }

    public Wallet execute(UUID userId) {
        User user = this.userPersistence.findById(userId);
        return this.walletPersistence.createWalletForUser(user);
    }

}
