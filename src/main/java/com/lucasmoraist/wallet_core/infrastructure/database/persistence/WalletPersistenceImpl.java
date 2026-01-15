package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.UserEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.repository.WalletRepository;
import com.lucasmoraist.wallet_core.infrastructure.mapper.UserMapper;
import com.lucasmoraist.wallet_core.infrastructure.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class WalletPersistenceImpl implements WalletPersistence {

    private final WalletRepository walletRepository;

    @Override
    public Wallet findById(UUID walletId) {
        return WalletMapper.toDomain(findEntityById(walletId));
    }

    @Override
    @Transactional
    public Wallet createWalletForUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        WalletEntity walletEntity = WalletEntity.builder()
                .user(userEntity)
                .balance(BigDecimal.ZERO)
                .version(1)
                .transactions(new ArrayList<>())
                .build();
        WalletEntity walletSaved = this.walletRepository.save(walletEntity);
        return WalletMapper.toDomain(walletSaved);
    }

    private WalletEntity findEntityById(UUID walletId) {
        return this.walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    log.error("Wallet not found: {}", walletId);
                    return new RuntimeException("Wallet not found");
                });
    }

}
