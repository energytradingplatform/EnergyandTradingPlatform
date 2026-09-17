package com.Trading.tradeservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType trade_type;
    private String commodity;
    private Double quantity;
    private Double price;
    private String currency;
    private Long counterparty_id;
    private String location;
    private String status;


    @Version
    @Column(name = "version", nullable = false)
    private Long version;


    private LocalDate tradeDate;
    private LocalDateTime updated_at;


}
