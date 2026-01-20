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
import java.math.RoundingMode;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditAmountCaseTest {

    @Mock
    WalletPersistence walletPersistence;
    @Mock
    TransactionalPersistence transactionalPersistence;
    @InjectMocks
    CreditAmountCase creditAmountCase;

    @Test
    @DisplayName("Should credit amount to wallet")
    void case01() {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);
        int installments = 2;
        BigDecimal installmentAmount = amount.divide(BigDecimal.valueOf(installments), 8, RoundingMode.HALF_UP);

        Wallet wallet = WalletFactory.createWallet(walletId);
        WalletTransaction transaction = TransactionalFactory.createTransactional(wallet, installmentAmount, PaymentType.CREDIT);

        when(walletPersistence.updateAmount(walletId, installmentAmount, PaymentType.CREDIT))
                .thenReturn(wallet);
        when(walletPersistence.findById(walletId)).thenReturn(wallet);

        when(transactionalPersistence.saveTransaction(wallet, installmentAmount, PaymentType.CREDIT))
                .thenReturn(transaction);

        WalletTransaction updatedWallet = creditAmountCase.execute(walletId, amount, installments);

        assertNotNull(updatedWallet);
        assertEquals(transaction, updatedWallet);
        verify(walletPersistence, times(2)).updateAmount(walletId, installmentAmount, PaymentType.CREDIT);
        verify(transactionalPersistence, times(2)).saveTransaction(wallet, installmentAmount, PaymentType.CREDIT);
    }

}