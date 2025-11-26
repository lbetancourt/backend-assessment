package io.paymeter.assessment.shared;

import io.paymeter.assessment.parking.domain.InvalidDateRangeException;
import io.paymeter.assessment.pricing.infrastructure.postgres.exception.PricingNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageResponse> handleUnknownException(Exception e) {
        log.error(e.getMessage(), e);
        var messageResponse = MessageResponse.builder()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.of(Optional.of(messageResponse));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<MessageResponse> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        var messageResponse = MessageResponse.builder()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.of(Optional.of(messageResponse));
    }

    @ExceptionHandler(PricingNotFoundException.class)
    protected ResponseEntity<MessageResponse> handlePricingNotFoundException(PricingNotFoundException e) {
        log.error(e.getMessage(), e);
        var messageResponse = MessageResponse.builder()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.of(Optional.of(messageResponse));
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    protected ResponseEntity<MessageResponse> handleInvalidDateRangeException(InvalidDateRangeException e) {
        log.error(e.getMessage(), e);
        var messageResponse = MessageResponse.builder()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.of(Optional.of(messageResponse));
    }
}
