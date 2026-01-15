package com.lucasmoraist.wallet_core.infrastructure.api.controller;

import com.lucasmoraist.wallet_core.application.usecases.transactions.CreditAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.DebitAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.DepositAmountCase;
import com.lucasmoraist.wallet_core.application.usecases.transactions.WithdrawAmountCase;
import com.lucasmoraist.wallet_core.domain.model.WalletTransaction;
import com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes.TransactionalDocumentationRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionalController implements TransactionalDocumentationRoutes {

    private final DepositAmountCase depositAmountCase;
    private final WithdrawAmountCase withdrawalAmountCase;
    private final DebitAmountCase debitAmountCase;
    private final CreditAmountCase creditAmountCase;

    @Override
    @PostMapping("/deposit/{walletId}")
    public ResponseEntity<Void> depositAmount(@PathVariable UUID walletId, @RequestParam BigDecimal amount) {
        WalletTransaction transaction = this.depositAmountCase.execute(walletId, amount);
        URI location = URI.create(String.format("/api/v1/transactions/%s", transaction.id()));
        return ResponseEntity.created(location).build();
    }

    @Override
    @PostMapping("/withdraw/{walletId}")
    public ResponseEntity<Void> withdrawAmount(@PathVariable UUID walletId, @RequestParam BigDecimal amount) {
        WalletTransaction transaction = this.withdrawalAmountCase.execute(walletId, amount);
        URI location = URI.create(String.format("/api/v1/transactions/%s", transaction.id()));
        return ResponseEntity.created(location).build();
    }

    @Override
    @PostMapping("/debit/{walletId}")
    public ResponseEntity<Void> debitAmount(@PathVariable UUID walletId, @RequestParam BigDecimal amount) {
        WalletTransaction transaction = this.debitAmountCase.execute(walletId, amount);
        URI location = URI.create(String.format("/api/v1/transactions/%s", transaction.id()));
        return ResponseEntity.created(location).build();
    }

    @Override
    @PostMapping("/credit/{walletId}")
    public ResponseEntity<Void> creditAmount(@PathVariable UUID walletId, @RequestParam BigDecimal amount,
                                             @RequestParam Integer installments) {
        WalletTransaction transaction = this.creditAmountCase.execute(walletId, amount, installments);
        URI location = URI.create(String.format("/api/v1/transactions/%s", transaction.id()));
        return ResponseEntity.created(location).build();
    }

}
