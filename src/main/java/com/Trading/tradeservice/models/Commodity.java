package com.Trading.tradeservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Commodity {
    @Id
    private Long id;
    private String code;
    private String name;
}
