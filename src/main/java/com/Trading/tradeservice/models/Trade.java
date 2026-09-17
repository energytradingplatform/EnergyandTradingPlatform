package com.Trading.tradeservice.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private TradeType trade_type;
    private String commodity;
    private Double quantity;
    private Double price;
    private String currency;
    private Long counterparty_id;
    private String location;
    private String status;
    private Long version;
    private LocalDate tradeDate;
    private LocalDateTime updated_at;
}
