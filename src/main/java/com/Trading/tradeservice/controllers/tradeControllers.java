package com.Trading.tradeservice.controllers;

import com.Trading.tradeservice.dtos.Request.TradeRequest;
import com.Trading.tradeservice.dtos.Response.TradeResponse;
import com.Trading.tradeservice.services.TradeService;
import com.Trading.tradeservice.validation.TradeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tradeControllers {

    private TradeService tradeService;
    private TradeValidator tradeValidator;

    public tradeControllers(TradeService tradeService, TradeValidator tradeValidator) {
        this.tradeService = tradeService;
        this.tradeValidator = tradeValidator;

    }

    @PostMapping("/api/v1/trades")
    public ResponseEntity<TradeResponse> createTrade(
            @RequestHeader("IdempotencyKey") String idempotencyKey,
            @RequestBody TradeRequest tradeRequest) {


        tradeService.captureTrade(idempotencyKey, tradeRequest);
        return ResponseEntity.ok(new TradeResponse());

        // Implementation for creating a trade
    }



}
