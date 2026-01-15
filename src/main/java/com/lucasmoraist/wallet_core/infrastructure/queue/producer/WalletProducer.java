package com.lucasmoraist.wallet_core.infrastructure.queue.producer;

import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class WalletProducer {

    private final StreamBridge streamBridge;

    public void sendMessage(Message<PaymentMessage> paymentMessage, String bindingName) {
        log.info("Sending message [{}] to binding [{}]", paymentMessage, bindingName);
        streamBridge.send(bindingName, paymentMessage);
    }

}
