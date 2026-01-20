package com.lucasmoraist.wallet_core.application.usecases.messages;

import com.lucasmoraist.wallet_core.application.gateway.MessageGateway;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.factory.PaymentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessMessagesTest {

    @Mock
    MessageGateway messageGateway;
    @InjectMocks
    ProcessMessages processMessages;

    @Test
    @DisplayName("Should send messages to transfer manager and notification service")
    void case01() {
        PaymentStatus status = PaymentStatus.COMPLETED;
        String statusReason = "Payment completed successfully";
        PaymentMessage message = PaymentFactory.buildPaymentMessage(status, statusReason);

        processMessages.execute(message, status, statusReason);

        verify(messageGateway, times(1)).sendToTransferManager(message);
        verify(messageGateway, times(1)).sendToNotificationSvc(message);
    }

}