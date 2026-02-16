package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.usecases.user.CreateUserCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByEmailCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByIdCase;
import com.lucasmoraist.wallet_core.application.usecases.wallet.AddWalletCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserUseCase {

    private final UserPersistence userPersistence;

    @Bean
    public CreateUserCase createUserCase(SecurityGateway securityGateway, AddWalletCase addWalletCase) {
        return new CreateUserCase(userPersistence, addWalletCase, securityGateway);
    }

    @Bean
    public GetUserByIdCase getUserByIdCase() {
        return new GetUserByIdCase(userPersistence);
    }

    @Bean
    public GetUserByEmailCase getUserByEmailCase() {
        return new GetUserByEmailCase(userPersistence);
    }

}
