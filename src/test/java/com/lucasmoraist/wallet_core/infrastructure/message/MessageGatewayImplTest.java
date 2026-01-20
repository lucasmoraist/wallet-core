package com.lucasmoraist.wallet_core.infrastructure.message;

import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.factory.PaymentFactory;
import com.lucasmoraist.wallet_core.infrastructure.queue.producer.WalletProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageGatewayImplTest {

    @Mock
    WalletProducer walletProducer;
    @InjectMocks
    MessageGatewayImpl messageGatewayImpl;

    @Test
    @DisplayName("Should send message to Transfer Manager (Payment COMPLETED) successfully")
    void case01() {
        PaymentMessage message = PaymentFactory.buildPaymentMessage();

        this.messageGatewayImpl.sendToTransferManager(message);

        verify(walletProducer, times(1)).sendMessage(any(), anyString());
    }

    @Test
    @DisplayName("Should send message to Transfer Manager (Payment FAILED) successfully")
    void case02() {
        PaymentMessage message = PaymentFactory.buildPaymentMessage(PaymentStatus.FAILED, "Insufficient funds");

        this.messageGatewayImpl.sendToTransferManager(message);

        verify(walletProducer, times(1)).sendMessage(any(), anyString());
    }

    @Test
    @DisplayName("Should send message to Notification Service (Payment COMPLETED) successfully")
    void case03() {
        PaymentMessage message = PaymentFactory.buildPaymentMessage();

        this.messageGatewayImpl.sendToNotificationSvc(message);

        verify(walletProducer, times(1)).sendMessage(any(), anyString());
    }

    @Test
    @DisplayName("Should send message to Notification Service (Payment FAILED) successfully")
    void case04() {
        PaymentMessage message = PaymentFactory.buildPaymentMessage(PaymentStatus.FAILED, "Insufficient funds");

        this.messageGatewayImpl.sendToNotificationSvc(message);

        verify(walletProducer, times(1)).sendMessage(any(), anyString());
    }

}