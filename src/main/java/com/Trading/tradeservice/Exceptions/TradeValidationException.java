package com.Trading.tradeservice.Exceptions;

public class TradeValidationException extends RuntimeException {
    public TradeValidationException(String message) {
        super(message);
    }
}
