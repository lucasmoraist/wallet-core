package com.lucasmoraist.wallet_core.infrastructure.api.handler;

import com.lucasmoraist.wallet_core.domain.exception.InsufficientFundsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    protected ResponseEntity<Void> handleInsufficientFunds(InsufficientFundsException ex) {
        log.warn("Message: {} - ", ex.getMessage(), ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Void> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Message: {} - ", ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Void> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Message: {} - ", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Void> handleGenericException(Exception ex) {
        log.error("Message: {} - ", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().build();
    }

}
