package com.lucasmoraist.wallet_core.application.messages;

import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.WithdrawAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByIdCase;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.domain.model.User;
import org.springframework.messaging.Message;

public class OrchestrationTransfer {

    private final GetUserByIdCase getUserByIdCase;
    private final DepositAmountCase depositAmountCase;
    private final WithdrawAmountCase withdrawAmountCase;
    private final ProcessMessages processMessages;

    public OrchestrationTransfer(GetUserByIdCase getUserByIdCase,
                                 DepositAmountCase depositAmountCase,
                                 WithdrawAmountCase withdrawAmountCase,
                                 ProcessMessages processMessages) {
        this.getUserByIdCase = getUserByIdCase;
        this.depositAmountCase = depositAmountCase;
        this.withdrawAmountCase = withdrawAmountCase;
        this.processMessages = processMessages;
    }

    public void execute(Message<PaymentMessage> message) {
        PaymentMessage payment = message.getPayload();

        try {
            User payer = getUserByIdCase.execute(payment.payer().payerId());
            User payee = getUserByIdCase.execute(payment.payee().payeeId());

            this.depositAmountCase.execute(payee.wallets().getFirst().id(), payment.amount());
            this.withdrawAmountCase.execute(payer.wallets().getFirst().id(), payment.amount());

            this.processMessages.execute(payment, PaymentStatus.COMPLETED);
        } catch (Exception ex) {
            this.processMessages.execute(payment, PaymentStatus.FAILED);
        }
    }

}
