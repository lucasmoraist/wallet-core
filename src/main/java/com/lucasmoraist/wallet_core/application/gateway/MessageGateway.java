package com.lucasmoraist.wallet_core.application.gateway;

import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;

public interface MessageGateway {

    void sendToTransferManager(PaymentMessage message);
    void sendToNotificationSvc(PaymentMessage message);

}
