package com.lucasmoraist.wallet_core.infrastructure.queue.consumer;

import com.lucasmoraist.wallet_core.application.usecases.messages.OrchestrationTransfer;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class WalletConsumer {

    private final OrchestrationTransfer orchestrationTransfer;

    @Bean
    public Consumer<Message<PaymentMessage>> fromProcessTransfer() {
        return orchestrationTransfer::execute;
    }

}
