package com.lucasmoraist.wallet_core.infrastructure.api.controller;

import com.lucasmoraist.wallet_core.application.usecases.wallet.AddWalletCase;
import com.lucasmoraist.wallet_core.application.usecases.wallet.ConsultBalanceCase;
import com.lucasmoraist.wallet_core.domain.model.Wallet;
import com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes.WalletDocumentationRoutes;
import com.lucasmoraist.wallet_core.infrastructure.api.dto.wallet.BalanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController implements WalletDocumentationRoutes {

    private final ConsultBalanceCase consultBalanceCase;
    private final AddWalletCase addWalletCase;

    @Override
    @PostMapping("/users/{userId}")
    public ResponseEntity<Void> addWallet(@PathVariable UUID userId) {
        Wallet wallet = this.addWalletCase.execute(userId);
        URI location = URI.create(String.format("/api/v1/wallets/%s", wallet.id()));
        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping("/{walletId}/consult-balance")
    public ResponseEntity<BalanceDTO> consultBalance(@PathVariable UUID walletId) {
        BigDecimal balance = this.consultBalanceCase.execute(walletId).balance();
        return ResponseEntity.ok().body(new BalanceDTO(balance));
    }

}
