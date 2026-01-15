package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.model.User;

import java.util.UUID;

public interface UserPersistence {

    User save(User user);
    User findById(UUID userId);

}
