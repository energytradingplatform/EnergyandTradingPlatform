package com.Trading.tradeservice.services;

import com.Trading.tradeservice.Exceptions.IdempotencyException;
import com.Trading.tradeservice.Exceptions.TradeConflictException;
import com.Trading.tradeservice.Exceptions.TradeNotFoundException;
import com.Trading.tradeservice.Exceptions.TradeValidationException;
import com.Trading.tradeservice.dtos.Request.TradeRequest;
import com.Trading.tradeservice.dtos.Request.UpdateTradeRequest;
import com.Trading.tradeservice.dtos.Response.TradeResponse;
import com.Trading.tradeservice.models.IdempotencyKey;
import com.Trading.tradeservice.models.IdempotencyStatus;
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
        public String captureTrade(String idempotencyKey, TradeRequest tradeRequest) {
            // Implementation for creating a trade


            tradeValidator.validate(idempotencyKey, tradeRequest);

            Optional<IdempotencyKey> existing =  idempotencyKeyRepository.findByIdempotencyKey(idempotencyKey);

            if(existing.isPresent()){
                if(!(existing.get().getRequestHash().equals(calculateHash(tradeRequest)))){
                    throw new IdempotencyException("Trade with this idempotency key already exists. Trade ID: " +existing.get().getIdempotencyKey()+" but different request");

                }
                String tradereturned = existing.get().getResponseBody();
               return tradereturned;
            }

            // Additional logic for capturing the trade
            Trade trade =   mapToEntity(tradeRequest);

            Trade savedTrade =  tradeRepository.save(trade);

            IdempotencyKey newIdempotencyKey = new IdempotencyKey();
            newIdempotencyKey.setIdempotencyKey(idempotencyKey);
            newIdempotencyKey.setTradeId(savedTrade.getId());
            newIdempotencyKey.setStatusCode(IdempotencyStatus.COMPLETED);
            newIdempotencyKey.setRequestHash(calculateHash(tradeRequest));
            newIdempotencyKey.setResponseBody(String.valueOf(savedTrade));
            newIdempotencyKey.setCreatedAt(LocalDateTime.now());



            idempotencyKeyRepository.save(newIdempotencyKey);

            return String.valueOf(mapToResponse(savedTrade));
        }


        public String updateTrade(Long tradeId, TradeRequest updateRequest){

            Optional<Trade> trade =  tradeRepository.findById(tradeId);
            if(trade  == null){
                throw new TradeNotFoundException("Trade not found");
            }

            if (! trade.get().getVersion().equals(updateRequest.getVersion())) {
                throw new TradeConflictException(
                        "Trade was modified by another user");
            }

            Trade updatedTrade =   mapToEntity(updateRequest);

            Trade savedTrade =  tradeRepository.save(updatedTrade);

                return "";
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
