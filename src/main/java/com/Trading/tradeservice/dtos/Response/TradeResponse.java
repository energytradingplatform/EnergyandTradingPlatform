package com.Trading.tradeservice.dtos.Response;


import com.Trading.tradeservice.models.TradeType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TradeResponse {

    private TradeType trade_type;


    private String commodity;


    private Double quantity;


    private Double price;


    private String currency;

    private Long counterparty_id;


    private String location;


    private LocalDate TradeDate;

}
