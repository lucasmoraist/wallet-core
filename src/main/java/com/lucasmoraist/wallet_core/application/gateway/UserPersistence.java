package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.model.User;

public interface UserPersistence {

    User save(User user);

}
