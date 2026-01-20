package com.lucasmoraist.wallet_core.application.usecases.messages;

import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.WithdrawAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.user.GetUserByIdCase;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.exception.InsufficientFundsException;
import com.lucasmoraist.wallet_core.domain.message.Payee;
import com.lucasmoraist.wallet_core.domain.message.Payer;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.domain.model.User;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrchestrationTransferTest {

    @Mock
    GetUserByIdCase getUserByIdCase;
    @Mock
    DepositAmountCase depositAmountCase;
    @Mock
    WithdrawAmountCase withdrawAmountCase;
    @Mock
    ProcessMessages processMessages;
    @Mock
    Message<PaymentMessage> messageWrapper;
    @Mock
    PaymentMessage paymentMessage;
    @InjectMocks
    OrchestrationTransfer orchestrationTransfer;

    private static final UUID PAYER_ID = UUID.randomUUID();
    private static final UUID PAYEE_ID = UUID.randomUUID();
    private static final UUID PAYER_WALLET_ID = UUID.randomUUID();
    private static final UUID PAYEE_WALLET_ID = UUID.randomUUID();
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100.00);

    @Test
    @DisplayName("Should execute transfer successfully")
    void case01() {
        setupMessageMock();
        User payer = mockUser(PAYER_WALLET_ID);
        User payee = mockUser(PAYEE_WALLET_ID);

        when(getUserByIdCase.execute(PAYER_ID)).thenReturn(payer);
        when(getUserByIdCase.execute(PAYEE_ID)).thenReturn(payee);

        orchestrationTransfer.execute(messageWrapper);

        verify(getUserByIdCase).execute(PAYER_ID);
        verify(getUserByIdCase).execute(PAYEE_ID);
        verify(withdrawAmountCase).execute(PAYER_WALLET_ID, AMOUNT);
        verify(depositAmountCase).execute(PAYEE_WALLET_ID, AMOUNT);
        verify(processMessages).execute(eq(paymentMessage), eq(PaymentStatus.COMPLETED), any());
    }

    @Test
    @DisplayName("Should handle InsufficientFundsException (FAILED - Insufficient Funds)")
    void case02() {
        setupMessageMock();
        User payer = mockUser(PAYER_WALLET_ID);
        User payee = mockUser(PAYEE_WALLET_ID);

        when(getUserByIdCase.execute(PAYER_ID)).thenReturn(payer);
        when(getUserByIdCase.execute(PAYEE_ID)).thenReturn(payee);

        doThrow(new InsufficientFundsException("Sem saldo"))
                .when(withdrawAmountCase).execute(PAYER_WALLET_ID, AMOUNT);

        orchestrationTransfer.execute(messageWrapper);

        verify(withdrawAmountCase).execute(PAYER_WALLET_ID, AMOUNT);
        verify(depositAmountCase, never()).execute(any(), any());
        verify(processMessages).execute(
                eq(paymentMessage),
                eq(PaymentStatus.FAILED),
                contains("insufficient funds")
        );
    }

    @Test
    @DisplayName("Should handle generic exception (FAILED - Internal Error)")
    void case03() {
        setupMessageMock();

        when(getUserByIdCase.execute(PAYER_ID)).thenThrow(new RuntimeException("Database error"));

        orchestrationTransfer.execute(messageWrapper);

        verifyNoInteractions(withdrawAmountCase);
        verifyNoInteractions(depositAmountCase);

        verify(processMessages).execute(
                paymentMessage,
                PaymentStatus.FAILED,
                "Payment failed due to an internal error."
        );
    }

    private void setupMessageMock() {
        lenient().when(messageWrapper.getPayload()).thenReturn(paymentMessage);

        Payer payerMock = mock(Payer.class);
        Payee payeeMock = mock(Payee.class);

        lenient().when(paymentMessage.payer()).thenReturn(payerMock);
        lenient().when(paymentMessage.payee()).thenReturn(payeeMock);
        lenient().when(paymentMessage.amount()).thenReturn(AMOUNT);

        lenient().when(payerMock.payerId()).thenReturn(PAYER_ID);
        lenient().when(payeeMock.payeeId()).thenReturn(PAYEE_ID);
    }

    private User mockUser(UUID walletId) {
        User user = mock(User.class);
        Wallet wallet = mock(Wallet.class);

        lenient().when(user.wallet()).thenReturn(wallet);
        lenient().when(wallet.id()).thenReturn(walletId);

        return user;
    }

}