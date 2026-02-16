package com.lucasmoraist.wallet_core.application.usecases.user;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;

public class GetUserByEmailCase {

    private final UserPersistence userPersistence;

    public GetUserByEmailCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User execute(String userEmail) {
        return this.userPersistence.findByEmail(userEmail);
    }

}
