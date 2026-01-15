package com.lucasmoraist.wallet_core.application.messages;

import com.lucasmoraist.wallet_core.application.gateway.MessageGateway;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;

public class ProcessMessages {

    private final MessageGateway messageGateway;

    public ProcessMessages(MessageGateway messageGateway) {
        this.messageGateway = messageGateway;
    }

    public void execute(PaymentMessage message, PaymentStatus status) {
        PaymentMessage paymentMessage = new PaymentMessage(message, status);
        this.messageGateway.sendToTransferManager(paymentMessage);
        this.messageGateway.sendToNotificationSvc(paymentMessage);
    }

}
