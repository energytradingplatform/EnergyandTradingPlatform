package com.Trading.tradeservice.Exceptions;

public class TradeNotFoundException extends  RuntimeException{
    public TradeNotFoundException(String message){
        super(message);
    }
}
