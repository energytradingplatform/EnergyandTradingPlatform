package com.Trading.tradeservice.models;

import com.Trading.tradeservice.dtos.Response.TradeResponse;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "idempotency_keys",
         uniqueConstraints = {
                 @UniqueConstraint(
                         name = "uk_idempotency_key",
                         columnNames = "idempotency_key"
                 )
         }
)
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="idempotencyKey", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name="request_hash", nullable = false)
    private String requestHash;

    @Column(name = "trade_id")
    private Long tradeId;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdempotencyStatus statusCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;



}
