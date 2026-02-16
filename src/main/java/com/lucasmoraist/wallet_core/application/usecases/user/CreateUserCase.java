package com.lucasmoraist.wallet_core.application.usecases.user;

import com.lucasmoraist.wallet_core.application.gateway.SecurityGateway;
import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.usecases.wallet.AddWalletCase;
import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;
import com.lucasmoraist.wallet_core.domain.model.User;

public class CreateUserCase {

    private final UserPersistence userPersistence;
    private final AddWalletCase addWalletCase;
    private final SecurityGateway securityGateway;

    public CreateUserCase(UserPersistence userPersistence, AddWalletCase addWalletCase, SecurityGateway securityGateway) {
        this.userPersistence = userPersistence;
        this.addWalletCase = addWalletCase;
        this.securityGateway = securityGateway;
    }

    public User execute(String fullName, String cpfCnpj, String email, String password) {
        User user = new User(
                null,
                fullName,
                cpfCnpj,
                email,
                this.securityGateway.encryptPassword(password),
                RolesEnum.getRoleByDocument(cpfCnpj),
                null,
                null,
                null
        );

        User savedUser = this.userPersistence.save(user);
        this.addWalletCase.execute(savedUser.id());

        return savedUser;
    }

}
