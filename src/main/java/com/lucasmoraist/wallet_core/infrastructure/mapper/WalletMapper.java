package com.lucasmoraist.wallet_core.infrastructure.mapper;

import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletEntity;

public final class WalletMapper {

    private WalletMapper() {
    }

    public static Wallet toDomain(WalletEntity walletEntity) {
        return new Wallet(
                walletEntity.getId(),
                null,
                walletEntity.getBalance(),
                walletEntity.getVersion(),
                walletEntity.getTransactions()
                        .stream()
                        .map(WalletTransactionMapper::toDomain)
                        .toList()
        );
    }

    public static WalletEntity toEntity(Wallet wallet) {
        return new WalletEntity(
                wallet.id() != null ? wallet.id() : null,
                null,
                wallet.balance(),
                wallet.version(),
                wallet.transactions() != null ? wallet.transactions()
                        .stream()
                        .map(WalletTransactionMapper::toEntity)
                        .toList() : null
        );
    }

}
