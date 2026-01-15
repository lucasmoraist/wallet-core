package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.application.usecases.wallet.ConsultBalanceCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletUseCase {

    @Bean
    public ConsultBalanceCase consultBalanceCase(WalletPersistence walletPersistence) {
        return new ConsultBalanceCase(walletPersistence);
    }

}
