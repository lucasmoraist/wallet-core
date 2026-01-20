package com.lucasmoraist.wallet_core.infrastructure.database.persistence;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;
import com.lucasmoraist.wallet_core.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class TransactionalPersistenceImplTest {

    @Autowired
    TransactionalPersistenceImpl transactionalPersistence;
    @Autowired
    UserPersistence userPersistence;
    @Autowired
    WalletPersistence walletPersistence;

    Wallet wallet;

    @BeforeEach
    void setUp() {
        User user = userPersistence.save(UserFactory.createUser());
        wallet = walletPersistence.createWalletForUser(user);
    }

    @Test
    @DisplayName("Should save transaction successfully")
    void case01() {
        WalletTransaction transaction = transactionalPersistence.saveTransaction(
                wallet,
                BigDecimal.valueOf(100),
                PaymentType.DEPOSIT
        );

        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(100), transaction.amount());
        assertEquals(PaymentType.DEPOSIT, transaction.type());
    }

}