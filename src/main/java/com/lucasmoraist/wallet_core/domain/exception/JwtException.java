package com.lucasmoraist.wallet_core.domain.exception;

public class JwtException extends RuntimeException {

    public JwtException(String message, Throwable exception) {
        super(message, exception);
    }

}
