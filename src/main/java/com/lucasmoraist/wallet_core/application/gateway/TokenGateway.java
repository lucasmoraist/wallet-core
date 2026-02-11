package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.model.User;

public interface TokenGateway {

    String generateToken(User user);
    String getSubject(String token);
    String getClaim(String token, String claim);

}
