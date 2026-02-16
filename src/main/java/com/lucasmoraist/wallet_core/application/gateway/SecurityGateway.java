package com.lucasmoraist.wallet_core.application.gateway;

public interface SecurityGateway {

    String encryptPassword(String password);
    boolean verifyPassword(String rawPassword, String encryptedPassword);
    String getUserEmailFromAuthentication();

}
