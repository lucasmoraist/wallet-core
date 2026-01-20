package com.lucasmoraist.wallet_core.application.usecases.user;

import com.lucasmoraist.wallet_core.application.gateway.UserPersistence;
import com.lucasmoraist.wallet_core.domain.enums.RolesEnum;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserCaseTest {

    @Mock
    UserPersistence userPersistence;
    @InjectMocks
    CreateUserCase createUserCase;

    @Test
    @DisplayName("Should create a new user COMMON successfully")
    void case01() {
        String cpfCnpj = "11122233344";

        User userExpected = UserFactory.createUser(cpfCnpj);

        when(userPersistence.save(any(User.class))).thenReturn(userExpected);

        User userResponse = this.createUserCase.execute(userExpected.fullName(), cpfCnpj, userExpected.email(), userExpected.password());

        assertNotNull(userResponse);
        assertEquals(userExpected.fullName(), userResponse.fullName());
        assertEquals(cpfCnpj, userResponse.cpfCnpj());
        assertEquals(userExpected.email(), userResponse.email());
        assertEquals(userExpected.password(), userResponse.password());
        assertEquals(RolesEnum.COMMON, userResponse.role());
        verify(userPersistence, times(1)).save(userExpected);
    }

    @Test
    @DisplayName("Should create a new user MERCHANT successfully")
    void case02() {
        String cpfCnpj = "11222333000181";

        User userExpected = UserFactory.createUser(cpfCnpj);

        when(userPersistence.save(any(User.class))).thenReturn(userExpected);

        User userResponse = this.createUserCase.execute(userExpected.fullName(), cpfCnpj, userExpected.email(), userExpected.password());

        assertNotNull(userResponse);
        assertEquals(userExpected.fullName(), userResponse.fullName());
        assertEquals(cpfCnpj, userResponse.cpfCnpj());
        assertEquals(userExpected.email(), userResponse.email());
        assertEquals(userExpected.password(), userResponse.password());
        assertEquals(RolesEnum.MERCHANT, userResponse.role());
        verify(userPersistence, times(1)).save(userExpected);
    }

}