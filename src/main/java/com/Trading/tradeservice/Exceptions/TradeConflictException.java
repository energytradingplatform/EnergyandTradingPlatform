package com.Trading.tradeservice.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TradeConflictException extends RuntimeException{


    public TradeConflictException(String message) {
        super(message);
    }
}
