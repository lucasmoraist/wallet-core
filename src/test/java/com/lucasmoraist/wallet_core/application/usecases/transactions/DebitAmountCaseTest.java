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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DebitAmountCaseTest {

    @Mock
    WalletPersistence walletPersistence;
    @Mock
    TransactionalPersistence transactionalPersistence;
    @InjectMocks
    DebitAmountCase debitAmountCase;

    @Test
    @DisplayName("Should debit amount from wallet")
    void case01() {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        Wallet wallet = WalletFactory.createWallet(walletId);
        WalletTransaction transaction = TransactionalFactory.createTransactional(wallet, amount, PaymentType.DEBIT);

        when(walletPersistence.updateAmount(walletId, amount, PaymentType.DEBIT))
                .thenReturn(wallet);
        when(transactionalPersistence.saveTransaction(wallet, amount, PaymentType.DEBIT))
                .thenReturn(transaction);

        WalletTransaction updatedWallet = debitAmountCase.execute(walletId, amount);

        assertNotNull(updatedWallet);
        assertEquals(transaction, updatedWallet);
        verify(walletPersistence, times(1)).updateAmount(walletId, amount, PaymentType.DEBIT);
        verify(transactionalPersistence, times(1)).saveTransaction(wallet, amount, PaymentType.DEBIT);
    }

}