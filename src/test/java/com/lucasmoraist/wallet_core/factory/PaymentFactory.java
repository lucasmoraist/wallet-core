package com.lucasmoraist.wallet_core.factory;

import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.Payee;
import com.lucasmoraist.wallet_core.domain.message.Payer;
import com.lucasmoraist.wallet_core.domain.message.Payflow;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public final class PaymentFactory {

    public static PaymentMessage buildPaymentMessage(PaymentStatus status, String statusReason) {
        return new PaymentMessage(
                UUID.randomUUID().toString(),
                new Payer(
                        UUID.randomUUID(),
                        "John Doe",
                        "johndoe@example.com"
                ),
                new Payee(
                        UUID.randomUUID(),
                        "Jane Smith",
                        "janesmith@example.com"
                ),
                BigDecimal.valueOf(100),
                status,
                statusReason,
                LocalDateTime.now(),
                new Payflow(
                        UUID.randomUUID()
                )
        );
    }

    public static PaymentMessage buildPaymentMessage() {
        return new PaymentMessage(
                UUID.randomUUID().toString(),
                new Payer(
                        UUID.randomUUID(),
                        "John Doe",
                        "johndoe@example.com"
                ),
                new Payee(
                        UUID.randomUUID(),
                        "Jane Smith",
                        "janesmith@example.com"
                ),
                BigDecimal.valueOf(100),
                PaymentStatus.COMPLETED,
                "Payment completed successfully.",
                LocalDateTime.now(),
                new Payflow(
                        UUID.randomUUID()
                )
        );
    }

    public static Message<PaymentMessage> buildMessage() {
        return new Message<>() {
            @Override
            public PaymentMessage getPayload() {
                return buildPaymentMessage();
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of());
            }
        };
    }

}
