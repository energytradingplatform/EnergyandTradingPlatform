package com.Trading.tradeservice.services;

import com.Trading.tradeservice.dtos.Request.TradeRequest;
import com.Trading.tradeservice.dtos.Response.TradeResponse;
import com.Trading.tradeservice.models.Trade;
import com.Trading.tradeservice.models.TradeType;
import com.Trading.tradeservice.respositories.*;
import com.Trading.tradeservice.validation.TradeValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TradeService {


    private TradeValidator tradeValidator;
    private Traderepository tradeRepository;

        public TradeService(TradeValidator tradeValidator, Traderepository tradeRepository) {
            this.tradeValidator = tradeValidator;
            this.tradeRepository = tradeRepository;
        }

        @Transactional
        public TradeResponse captureTrade(TradeRequest tradeRequest) {
            // Implementation for creating a trade
            tradeValidator.validate(tradeRequest);
            // Additional logic for capturing the trade
            Trade trade =   mapToEntity(tradeRequest);
            Trade savedTrade =  tradeRepository.save(trade);
            return mapToResponse(savedTrade);
        }



        private Trade mapToEntity(TradeRequest request) {
            // mapping
            Trade trade = new Trade();
            trade.setTrade_type(request.getTrade_type());
            trade.setCommodity(request.getCommodity());
            trade.setQuantity(request.getQuantity());
            trade.setPrice(request.getPrice());
            trade.setCurrency(request.getCurrency());
            trade.setCounterparty_id(request.getCounterparty_id());
            trade.setLocation(request.getLocation());
            trade.setTradeDate(request.getTradeDate());
            return trade;

        }

        private TradeResponse mapToResponse(Trade trade) {
            // mapping
            TradeResponse tradeResponse = new TradeResponse();
            tradeResponse.setTrade_type(trade.getTrade_type());
            tradeResponse.setCommodity(trade.getCommodity());
            tradeResponse.setQuantity(trade.getQuantity());
            tradeResponse.setPrice(trade.getPrice());
            tradeResponse.setCurrency(trade.getCurrency());
            tradeResponse.setCounterparty_id(trade.getCounterparty_id());
            tradeResponse.setLocation(trade.getLocation());
            tradeResponse.setTradeDate(trade.getTradeDate());
            return tradeResponse;
        }


}
