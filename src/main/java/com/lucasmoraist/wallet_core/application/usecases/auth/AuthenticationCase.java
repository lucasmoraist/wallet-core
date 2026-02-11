package com.lucasmoraist.wallet_core.application.usecases.auth;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import com.lucasmoraist.wallet_core.application.gateway.TokenGateway;
import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;

public class AuthenticationCase {

    private final UserPersistence userPersistence;
    private final TokenGateway tokenGateway;
    private final SecurityGateway securityGateway;

    public AuthenticationCase(UserPersistence userPersistence, TokenGateway tokenGateway, SecurityGateway securityGateway) {
        this.userPersistence = userPersistence;
        this.tokenGateway = tokenGateway;
        this.securityGateway = securityGateway;
    }

    public String execute(String email, String password) {
        User user = this.userPersistence.findByEmail(email);
        if (!this.securityGateway.verifyPassword(password, user.password())) {
            throw new RuntimeException("Invalid credentials");
        }
        return this.tokenGateway.generateToken(user);
    }

}
