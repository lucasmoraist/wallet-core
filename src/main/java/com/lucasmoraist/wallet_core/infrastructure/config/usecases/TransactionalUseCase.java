package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.application.usecases.transactions.CreditAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.DebitAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.WithdrawAmountCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TransactionalUseCase {

    private final WalletPersistence walletPersistence;
    private final TransactionalPersistence transactionalPersistence;

    @Bean
    public DepositAmountCase depositAmountCase() {
        return new DepositAmountCase(walletPersistence, transactionalPersistence);
    }

    @Bean
    public WithdrawAmountCase withdrawAmountCase() {
        return new WithdrawAmountCase(walletPersistence, transactionalPersistence);
    }

    @Bean
    public DebitAmountCase createDepositAmountCase() {
        return new DebitAmountCase(walletPersistence, transactionalPersistence);
    }

    @Bean
    public CreditAmountCase creditAmountCase() {
        return new CreditAmountCase(walletPersistence, transactionalPersistence);
    }

}
