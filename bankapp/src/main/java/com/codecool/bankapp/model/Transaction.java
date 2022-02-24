package com.codecool.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Transaction {
    private final LocalDateTime transactionTime = LocalDateTime.now();
    private BigDecimal amount;
    private CurrencyType currency;
    private UUID sender;
    private UUID recipient;
    @Builder.Default
    private String message = "";
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PROCESSING;
}
