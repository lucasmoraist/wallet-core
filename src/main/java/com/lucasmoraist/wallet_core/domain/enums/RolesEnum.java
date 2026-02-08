package com.lucasmoraist.wallet_core.domain.enums;

public enum RolesEnum {
    COMMON,
    ADMIN,
    MERCHANT;

    public static RolesEnum getRoleByDocument(String cpfCnpj) {
        String cleanedDocument = cpfCnpj.replaceAll("\\D", "");
        if (cleanedDocument.length() == 11) {
            return COMMON;
        } else if (cleanedDocument.length() == 14) {
            return MERCHANT;
        } else {
            throw new IllegalArgumentException("Invalid document length");
        }
    }
}
