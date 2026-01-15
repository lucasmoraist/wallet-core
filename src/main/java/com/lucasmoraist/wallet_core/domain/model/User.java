package com.lucasmoraist.wallet_core.domain.model;

import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record User(
        UUID id,
        String fullName,
        String cpfCnpj,
        String email,
        String password,
        RolesEnum role,
        List<Wallet> wallets,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
