package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.exception.InsufficientFundsException;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.factory.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class WalletPersistenceImplTest {

    @Autowired
    WalletPersistenceImpl walletPersistenceImpl;
    @Autowired
    UserPersistence userPersistence;

    User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser();
        user = userPersistence.save(user);
    }

    @Test
    @DisplayName("Should create wallet for user")
    void case01() {
        Wallet wallet = createWalletForUser();

        assertNotNull(wallet);
        assertNotNull(wallet.id());
        assertEquals(BigDecimal.ZERO, wallet.balance());
        assertEquals(1, wallet.version());
        assertTrue(wallet.transactions().isEmpty());
    }

    @Test
    @DisplayName("Should find wallet by id")
    void case02() {
        Wallet wallet = createWalletForUser();

        Wallet walletFound = walletPersistenceImpl.findById(wallet.id());

        assertNotNull(walletFound);
    }

    @Test
    @DisplayName("Should throw exception when wallet not found by id")
    void case03() {
        assertThrows(EntityNotFoundException.class,
                () -> walletPersistenceImpl.findById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should update wallet amount for DEPOSIT")
    void case04() {
        Wallet wallet = createWalletForUser();

        BigDecimal depositAmount = new BigDecimal("100.00");
        Wallet updatedWallet = walletPersistenceImpl.updateAmount(wallet.id(), depositAmount, PaymentType.DEPOSIT);

        assertNotNull(updatedWallet);
        assertEquals(depositAmount, updatedWallet.balance());
    }

    @Test
    @DisplayName("Should update wallet amount for WITHDRAWAL")
    void case05() {
        Wallet wallet = createWalletForUser();

        BigDecimal depositAmount = new BigDecimal("200.00");
        wallet = walletPersistenceImpl.updateAmount(wallet.id(), depositAmount, PaymentType.DEPOSIT);

        BigDecimal withdrawalAmount = new BigDecimal("50.00");
        Wallet updatedWallet = walletPersistenceImpl.updateAmount(wallet.id(), withdrawalAmount, PaymentType.WITHDRAWAL);

        assertNotNull(updatedWallet);
        assertEquals(new BigDecimal("150.00"), updatedWallet.balance());
    }

    @Test
    @DisplayName("Should throw exception for insufficient funds on WITHDRAWAL")
    void case06() {
        Wallet wallet = createWalletForUser();

        BigDecimal withdrawalAmount = new BigDecimal("50.00");
        assertThrows(InsufficientFundsException.class,
                () -> walletPersistenceImpl.updateAmount(wallet.id(), withdrawalAmount, PaymentType.WITHDRAWAL));
    }

    private Wallet createWalletForUser() {
        return walletPersistenceImpl.createWalletForUser(user);
    }

}