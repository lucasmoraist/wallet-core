package com.lucasmoraist.wallet_core.application.usecases.user;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.factory.UserFactory;
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
class GetUserByIdCaseTest {

    @Mock
    UserPersistence userPersistence;
    @InjectMocks
    GetUserByIdCase getUserByIdCase;

    @Test
    @DisplayName("Should create a new user COMMON successfully")
    void case01() {
        UUID userId = UUID.randomUUID();
        User userExpected = UserFactory.createUser(userId);

        when(userPersistence.findById(userId)).thenReturn(userExpected);

        User userResponse = this.getUserByIdCase.execute(userId);

        assertNotNull(userResponse);
        verify(userPersistence, times(1)).findById(userId);
    }

}