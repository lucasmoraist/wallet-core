package com.lucasmoraist.wallet_core.application.usecases.transactions;

import com.lucasmoraist.wallet_core.application.gateway.TransactionalPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.enums.PaymentType;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;
import com.lucasmoraist.wallet_core.factory.TransactionalFactory;
import com.lucasmoraist.wallet_core.factory.WalletFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositAmountCaseTest {

    @Mock
    WalletPersistence walletPersistence;
    @Mock
    TransactionalPersistence transactionalPersistence;
    @InjectMocks
    DepositAmountCase depositAmountCase;

    @Test
    @DisplayName("Should deposit amount from wallet")
    void case01() {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        Wallet wallet = WalletFactory.createWallet(walletId);
        WalletTransaction transaction = TransactionalFactory.createTransactional(wallet, amount, PaymentType.DEPOSIT);

        when(walletPersistence.updateAmount(walletId, amount, PaymentType.DEPOSIT))
                .thenReturn(wallet);
        when(transactionalPersistence.saveTransaction(wallet, amount, PaymentType.DEPOSIT))
                .thenReturn(transaction);

        WalletTransaction updatedWallet = depositAmountCase.execute(walletId, amount);

        assertNotNull(updatedWallet);
        assertEquals(transaction, updatedWallet);
        verify(walletPersistence, times(1)).updateAmount(walletId, amount, PaymentType.DEPOSIT);
        verify(transactionalPersistence, times(1)).saveTransaction(wallet, amount, PaymentType.DEPOSIT);
    }

}