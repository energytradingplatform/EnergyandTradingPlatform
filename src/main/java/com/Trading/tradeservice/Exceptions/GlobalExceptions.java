package com.Trading.tradeservice.Exceptions;

import com.Trading.tradeservice.dtos.Response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {


    @ExceptionHandler(TradeValidationException.class)
    public ResponseEntity<ErrorResponse> handleTradeException(TradeValidationException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse( "Trade_Validation_Failed", e.getMessage())
                );
    }

}
