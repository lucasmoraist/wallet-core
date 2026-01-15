package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletTransactionEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.repository.WalletTransactionalRepository;
import com.lucasmoraist.wallet_core.infrastructure.mapper.WalletMapper;
import com.lucasmoraist.wallet_core.infrastructure.mapper.WalletTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
@RequiredArgsConstructor
public class TransactionalPersistenceImpl implements TransactionalPersistence {

    private final WalletTransactionalRepository walletTransactionalRepository;

    @Override
    public WalletTransaction saveTransaction(Wallet wallet, BigDecimal amount, PaymentType paymentType) {
        WalletEntity walletEntity = WalletMapper.toEntity(wallet);
        WalletTransactionEntity transactionEntity = WalletTransactionEntity.builder()
                .wallet(walletEntity)
                .amount(amount)
                .type(paymentType)
                .build();
        WalletTransactionEntity transactionSaved = this.walletTransactionalRepository.save(transactionEntity);
        return WalletTransactionMapper.toDomain(transactionSaved);
    }

}
