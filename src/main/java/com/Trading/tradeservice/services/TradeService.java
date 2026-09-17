package com.Trading.tradeservice.services;

import com.Trading.tradeservice.Exceptions.IdempotencyException;
import com.Trading.tradeservice.Exceptions.TradeValidationException;
import com.Trading.tradeservice.dtos.Request.TradeRequest;
import com.Trading.tradeservice.dtos.Response.TradeResponse;
import com.Trading.tradeservice.models.IdempotencyKey;
import com.Trading.tradeservice.models.Trade;
import com.Trading.tradeservice.models.TradeType;
import com.Trading.tradeservice.respositories.*;
import com.Trading.tradeservice.validation.TradeValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TradeService {


    private TradeValidator tradeValidator;
    private Traderepository tradeRepository;

    private final IdempotencyKeyRepository idempotencyKeyRepository;

        public TradeService(TradeValidator tradeValidator, Traderepository tradeRepository, IdempotencyKeyRepository idempotencyKeyRepository) {
            this.tradeValidator = tradeValidator;
            this.tradeRepository = tradeRepository;
            this.idempotencyKeyRepository = idempotencyKeyRepository;
        }

        @Transactional
        public TradeResponse captureTrade(String idempotencyKey, TradeRequest tradeRequest) {
            // Implementation for creating a trade


            tradeValidator.validate(idempotencyKey, tradeRequest);

            Optional<IdempotencyKey> existing =  idempotencyKeyRepository.findByIdempotencyKey(idempotencyKey);

            if(existing.isPresent()){

               throw new IdempotencyException("Trade with this idempotency key already exists. Trade ID: " +existing.get().getIdempotencyKey());
            }

            // Additional logic for capturing the trade
            Trade trade =   mapToEntity(tradeRequest);
            Trade savedTrade =  tradeRepository.save(trade);

            IdempotencyKey newIdempotencyKey = new IdempotencyKey();
            newIdempotencyKey.setId(idempotencyKey);
            newIdempotencyKey.setTradeId(savedTrade.getId());
            newIdempotencyKey.setRequestHash(calculateHash(tradeRequest));
            newIdempotencyKey.setResponseBody(String.valueOf(mapToResponse(savedTrade)));
            newIdempotencyKey.setCreatedAt(LocalDateTime.now());


            idempotencyKeyRepository.save(newIdempotencyKey);

            return mapToResponse(savedTrade);
        }

        private String calculateHash(TradeRequest tradeRequest) {
            // Implementation for calculating hash
            return tradeRequest.hashCode() + "";
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
