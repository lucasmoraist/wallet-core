package com.lucasmoraist.wallet_core.domain.message;

import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentMessage(
        String transferId,
        Payer payer,
        Payee payee,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt,
        Payflow payflow
) {
    public PaymentMessage(PaymentMessage message, PaymentStatus status) {
        this(
                message.transferId(),
                message.payer(),
                message.payee(),
                message.amount(),
                status,
                message.createdAt(),
                message.payflow()
        );
    }
}
