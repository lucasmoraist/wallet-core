package com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes;

import com.lucasmoraist.wallet_core.infrastructure.api.dto.wallet.BalanceDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface WalletDocumentationRoutes {

    ResponseEntity<Void> addWallet(UUID userId);
    ResponseEntity<BalanceDTO> consultBalance(UUID walletId);

}
