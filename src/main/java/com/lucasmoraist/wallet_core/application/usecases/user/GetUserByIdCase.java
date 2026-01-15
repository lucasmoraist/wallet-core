package com.lucasmoraist.wallet_core.application.usecases.user;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;

import java.util.UUID;

public class GetUserByIdCase {

    private final UserPersistence userPersistence;

    public GetUserByIdCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User execute(UUID userId) {
        return this.userPersistence.findById(userId);
    }

}
