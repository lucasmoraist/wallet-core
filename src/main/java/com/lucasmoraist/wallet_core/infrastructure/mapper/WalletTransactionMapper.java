package com.lucasmoraist.wallet_core.infrastructure.mapper;

import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletTransactionEntity;

public final class WalletTransactionMapper {

    private WalletTransactionMapper() {
    }

    public static WalletTransaction toDomain(WalletTransactionEntity walletTransactionEntity) {
        return new WalletTransaction(
                walletTransactionEntity.getId(),
                null,
                walletTransactionEntity.getAmount(),
                walletTransactionEntity.getType(),
                walletTransactionEntity.getCreatedAt()
        );
    }

    public static WalletTransactionEntity toEntity(WalletTransaction walletTransaction) {
        return new WalletTransactionEntity(
                walletTransaction.id() != null ? walletTransaction.id() : null,
                null,
                walletTransaction.amount(),
                walletTransaction.type(),
                walletTransaction.createdAt()
        );
    }

}
