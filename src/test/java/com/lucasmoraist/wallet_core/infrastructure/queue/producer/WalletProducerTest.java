package com.lucasmoraist.wallet_core.infrastructure.queue.producer;

import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.factory.PaymentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WalletProducerTest {

    @Mock
    StreamBridge streamBridge;
    @InjectMocks
    WalletProducer walletProducer;

    @Test
    @DisplayName("Should send message to the specified binding successfully")
    void case01() {
        Message<PaymentMessage> message = PaymentFactory.buildMessage();
        String bindingName = "toProcessTransfer-out-0";

        walletProducer.sendMessage(message, bindingName);

        verify(streamBridge, times(1)).send(bindingName, message);
    }

}