package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import com.lucasmoraist.wallet_core.application.gateway.TokenGateway;
import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.usecases.auth.AuthenticationCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationUseCase {

    @Bean
    public AuthenticationCase authenticationCase(
            UserPersistence userPersistence,
            TokenGateway tokenGateway,
            SecurityGateway securityGateway
    ) {
        return new AuthenticationCase(userPersistence, tokenGateway, securityGateway);
    }

}
