package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.usecases.user.CreateUserCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCase {

    @Bean
    public CreateUserCase createUserCase(UserPersistence userPersistence) {
        return new CreateUserCase(userPersistence);
    }

}
