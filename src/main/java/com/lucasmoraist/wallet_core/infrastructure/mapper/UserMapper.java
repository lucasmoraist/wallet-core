package com.lucasmoraist.wallet_core.infrastructure.mapper;

import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.UserEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getFullName(),
                entity.getCpfCnpj(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getWallet() != null
                        ? WalletMapper.toDomain(entity.getWallet())
                        : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserEntity toEntity(User user) {
        WalletEntity walletEntity = user.wallet() != null
                ? WalletMapper.toEntity(user.wallet())
                : null;

        UserEntity userEntity = new UserEntity(
                user.id(),
                user.fullName(),
                user.cpfCnpj(),
                user.email(),
                user.password(),
                user.role(),
                walletEntity,
                user.createdAt(),
                user.updatedAt()
        );

        if (walletEntity != null) {
            walletEntity.setUser(userEntity);
        }

        return userEntity;
    }

}
