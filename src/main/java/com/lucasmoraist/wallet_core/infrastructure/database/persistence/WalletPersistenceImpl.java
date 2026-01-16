package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.exception.InsufficientFundsException;
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
        log.debug("Finding wallet by id: {}", walletId);
        Wallet wallet = WalletMapper.toDomain(findEntityById(walletId));
        log.debug("Wallet found: {}", wallet);
        return wallet;
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
        log.debug("Wallet created for user {}: {}", user.id(), walletSaved);
        return WalletMapper.toDomain(walletSaved);
    }

    @Override
    @Transactional
    public Wallet updateAmount(UUID walletId, BigDecimal amount, PaymentType paymentType) {
        WalletEntity walletEntity = findEntityById(walletId);

        switch (paymentType) {
            case DEPOSIT -> {
                log.debug("Processing DEPOSIT of {} to wallet {}", amount, walletId);
                walletEntity.setBalance(walletEntity.getBalance().add(amount));
                log.debug("New balance after DEPOSIT: {}", walletEntity.getBalance());
            }
            case WITHDRAWAL, DEBIT, CREDIT -> {
                log.debug("Processing {} of {} from wallet {}", paymentType, amount, walletId);
                this.validateSufficientFunds(walletEntity, amount);
                walletEntity.setBalance(walletEntity.getBalance().subtract(amount));
                log.debug("New balance after {}: {}", paymentType, walletEntity.getBalance());
            }
            default -> {
                log.error("Unsupported payment type: {}", paymentType);
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
            }
        }

        return WalletMapper.toDomain(walletEntity);
    }

    private WalletEntity findEntityById(UUID walletId) {
        return this.walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    log.error("Wallet not found: {}", walletId);
                    return new RuntimeException("Wallet not found");
                });
    }

    private void validateSufficientFunds(WalletEntity entity, BigDecimal amount) {
        if (entity.getBalance().compareTo(amount) < 0) {
            log.error("Insufficient funds: current balance {}, attempted amount {}",
                    entity.getBalance(), amount);
            throw new InsufficientFundsException("Insufficient funds");
        }
    }

}
