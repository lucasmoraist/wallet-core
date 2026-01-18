package com.lucasmoraist.wallet_core.infrastructure.config.usecases;

import com.lucasmoraist.wallet_core.application.gateway.MessageGateway;
import com.lucasmoraist.wallet_core.application.usecases.messages.OrchestrationTransfer;
import com.lucasmoraist.wallet_core.application.usecases.messages.ProcessMessages;
import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.WithdrawAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByIdCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MessageUseCases {

    private final GetUserByIdCase getUserByIdCase;
    private final DepositAmountCase depositAmountCase;
    private final WithdrawAmountCase withdrawAmountCase;
    private final MessageGateway messageGateway;

    @Bean
    public OrchestrationTransfer orchestrationTransfer(ProcessMessages processMessages) {
        return new OrchestrationTransfer(
                getUserByIdCase,
                depositAmountCase,
                withdrawAmountCase,
                processMessages
        );
    }

    @Bean
    public ProcessMessages processMessages() {
        return new ProcessMessages(messageGateway);
    }

}
