package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionalUseCase {

    @Bean
    public DepositAmountCase depositAmountCase(
            WalletPersistence walletPersistence,
            TransactionalPersistence transactionalPersistence
    ) {
        return new DepositAmountCase(walletPersistence, transactionalPersistence);
    }

}
