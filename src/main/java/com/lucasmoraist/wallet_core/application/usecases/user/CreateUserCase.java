package com.lucasmoraist.wallet_core.application.usecases.user;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;
import com.lucasmoraist.wallet_core.domain.model.User;

public class CreateUserCase {

    private final UserPersistence userPersistence;

    public CreateUserCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User execute(String fullName, String cpfCnpj, String email, String password) {
        User user = new User(
                null,
                fullName,
                cpfCnpj,
                email,
                password,
                RolesEnum.getRoleByDocument(cpfCnpj),
                null,
                null,
                null
        );

        return this.userPersistence.save(user);
    }

}
