package com.lucasmoraist.wallet_core.factory;

import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;
import com.lucasmoraist.wallet_core.domain.model.User;

import java.util.UUID;

public final class UserFactory {

    public static User createUser() {
        return new User(
                null,
                "John Doe",
                "11122233344",
                "johndoe@example.com",
                "password123",
                RolesEnum.getRoleByDocument("11122233344"),
                null,
                null,
                null
        );
    }

    public static User createUser(UUID userId) {
        return new User(
                userId,
                "John Doe",
                "11122233344",
                "johndoe@example.com",
                "password123",
                RolesEnum.getRoleByDocument("11122233344"),
                null,
                null,
                null
        );
    }

    public static User createUser(UUID userId, String cpfCnpj) {
        return new User(
                userId,
                "John Doe",
                cpfCnpj,
                "johndoe@example.com",
                "password123",
                RolesEnum.getRoleByDocument(cpfCnpj),
                null,
                null,
                null
        );
    }

}
