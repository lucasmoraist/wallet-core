package com.lucasmoraist.wallet_core.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolesEnumTest {

    @Test
    @DisplayName("Should return COMMON role for CPF document")
    void case01() {
        String cpf = "11122233344";
        RolesEnum role = RolesEnum.getRoleByDocument(cpf);
        assertEquals(RolesEnum.COMMON, role);
    }

    @Test
    @DisplayName("Should return MERCHANT role for CNPJ document")
    void case02() {
        String cnpj = "11222333000181";
        RolesEnum role = RolesEnum.getRoleByDocument(cnpj);
        assertEquals(RolesEnum.MERCHANT, role);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid document length")
    void case03() {
        String invalidDocument = "123456789";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            RolesEnum.getRoleByDocument(invalidDocument);
        });
        assertEquals("Invalid document length", exception.getMessage());
    }

}