package com.lucasmoraist.wallet_core.application.usecases.wallet;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.application.gateway.WalletPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.factory.UserFactory;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddWalletCaseTest {

    @Mock
    UserPersistence userPersistence;
    @Mock
    WalletPersistence walletPersistence;
    @InjectMocks
    AddWalletCase addWalletCase;

    @Test
    @DisplayName("Should add a new wallet successfully")
    void case01() {
        UUID userId = UUID.randomUUID();

        User user = UserFactory.createUser(userId);
        Wallet wallet = WalletFactory.createWallet(user);

        when(userPersistence.findById(userId)).thenReturn(user);
        when(walletPersistence.createWalletForUser(user)).thenReturn(wallet);

        Wallet createdWallet = addWalletCase.execute(userId);

        assertEquals(user.id(), createdWallet.user().id());
        assertEquals(BigDecimal.valueOf(1000), createdWallet.balance());
        assertEquals(0, createdWallet.version());
        verify(userPersistence, times(1)).findById(userId);
        verify(walletPersistence, times(1)).createWalletForUser(user);
    }

}