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
        String statusReason,
        LocalDateTime createdAt,
        Payflow payflow
) {

    public PaymentMessage(PaymentMessage message, PaymentStatus status, String statusReason) {
        this(
                message.transferId(),
                message.payer(),
                message.payee(),
                message.amount(),
                status,
                statusReason,
                message.createdAt(),
                message.payflow()
        );
    }

}
