package com.Trading.tradeservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Currency {
    @Id
    private Long id;
    private String code;
    private String name;

}
