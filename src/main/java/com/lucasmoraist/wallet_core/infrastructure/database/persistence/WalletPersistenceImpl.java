package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.infrastructure.database.entity.WalletEntity;
import com.lucasmoraist.wallet_core.infrastructure.database.repository.WalletRepository;
import com.lucasmoraist.wallet_core.infrastructure.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

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

    private WalletEntity findEntityById(UUID walletId) {
        return this.walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    log.error("Wallet not found: {}", walletId);
                    return new RuntimeException("Wallet not found");
                });
    }

}
