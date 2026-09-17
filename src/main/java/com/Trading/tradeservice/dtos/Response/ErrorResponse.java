package com.Trading.tradeservice.dtos.Response;

import lombok.Data;

@Data
public class ErrorResponse {


    String code;
    String message;

    public ErrorResponse(String tradeValidationFailed, String message) {
        this.code = tradeValidationFailed;
        this.message = message;
    }
}
