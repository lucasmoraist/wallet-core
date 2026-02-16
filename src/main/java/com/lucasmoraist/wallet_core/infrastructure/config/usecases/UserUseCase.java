package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.usecases.user.CreateUserCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByIdCase;
import com.lucasmoraist.wallet_core.application.usecases.wallet.AddWalletCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCase {

    @Bean
    public CreateUserCase createUserCase(UserPersistence userPersistence, SecurityGateway securityGateway, AddWalletCase addWalletCase) {
        return new CreateUserCase(userPersistence, addWalletCase, securityGateway);
    }

    @Bean
    public GetUserByIdCase getUserByIdCase(UserPersistence userPersistence) {
        return new GetUserByIdCase(userPersistence);
    }

}
