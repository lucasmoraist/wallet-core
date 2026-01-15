package com.lucasmoraist.wallet_core.infrastructure.mapper;

import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.UserEntity;

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
                entity.getWallets()
                        .stream()
                        .map(WalletMapper::toDomain)
                        .toList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.id() != null ? user.id() : null,
                user.fullName(),
                user.cpfCnpj(),
                user.email(),
                user.password(),
                user.role(),
                user.wallets() != null ? user.wallets()
                        .stream()
                        .map(WalletMapper::toEntity)
                        .toList() : null,
                user.createdAt() != null ? user.createdAt() : null,
                user.updatedAt() != null ? user.updatedAt() : null
        );
    }

}
