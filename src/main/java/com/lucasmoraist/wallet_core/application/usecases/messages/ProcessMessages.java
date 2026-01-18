package com.lucasmoraist.wallet_core.application.usecases.messages;

import com.lucasmoraist.wallet_core.application.gateway.MessageGateway;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;

public class ProcessMessages {

    private final MessageGateway messageGateway;

    public ProcessMessages(MessageGateway messageGateway) {
        this.messageGateway = messageGateway;
    }

    public void execute(PaymentMessage message, PaymentStatus status, String statusReason) {
        PaymentMessage paymentMessage = new PaymentMessage(message, status, statusReason);
        this.messageGateway.sendToTransferManager(paymentMessage);
        this.messageGateway.sendToNotificationSvc(paymentMessage);
    }

}
