package com.lucasmoraist.wallet_core.infrastructure.api.documentation.routes;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionalDocumentationRoutes {

    ResponseEntity<Void> depositAmount(UUID walletId, BigDecimal amount);

}
