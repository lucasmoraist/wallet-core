package com.lucasmoraist.wallet_core.application.messages;

import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.WithdrawAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByIdCase;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.exception.InsufficientFundsException;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

public class OrchestrationTransfer {

    private static final Logger log = LoggerFactory.getLogger(OrchestrationTransfer.class);

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
        log.info("Received payment message: {}", payment);

        try {
            User payer = getUserByIdCase.execute(payment.payer().payerId());
            log.debug("Payer retrieved: {}", payer);
            User payee = getUserByIdCase.execute(payment.payee().payeeId());
            log.debug("Payee retrieved: {}", payee);

            this.withdrawAmountCase.execute(payer.wallets().getFirst().id(), payment.amount());
            this.depositAmountCase.execute(payee.wallets().getFirst().id(), payment.amount());

            log.info("Payment processed successfully: {}", payment);
            this.processMessages.execute(payment, PaymentStatus.COMPLETED, payment.statusReason());
        } catch (InsufficientFundsException ex) {
            log.warn("Insufficient funds for payment: {}", payment, ex);
            this.processMessages.execute(payment, PaymentStatus.FAILED, "Payment failed due to insufficient funds.");
        } catch (Exception ex) {
            log.warn("Failed to process payment: {}", payment, ex);
            this.processMessages.execute(payment, PaymentStatus.FAILED, "Payment failed due to an internal error.");
        }
    }

}
