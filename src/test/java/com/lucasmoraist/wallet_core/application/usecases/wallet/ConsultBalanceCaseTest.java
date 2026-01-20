package com.lucasmoraist.wallet_core.application.usecases.wallet;

import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.factory.WalletFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultBalanceCaseTest {

    @Mock
    WalletPersistence walletPersistence;
    @InjectMocks
    ConsultBalanceCase consultBalanceCase;

    @Test
    @DisplayName("Should consult wallet balance successfully")
    void case01() {
        UUID walletId = UUID.randomUUID();

        Wallet walletExpect = WalletFactory.createWallet(walletId);

        when(walletPersistence.findById(walletId)).thenReturn(walletExpect);

        Wallet response = consultBalanceCase.execute(walletId);

        assertNotNull(response);
        verify(walletPersistence, times(1)).findById(walletId);
    }

}