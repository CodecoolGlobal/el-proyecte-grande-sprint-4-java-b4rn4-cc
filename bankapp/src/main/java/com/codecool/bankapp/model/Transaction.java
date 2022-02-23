package com.codecool.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Transaction {
    private final LocalDateTime transactionTime = LocalDateTime.now();
    private BigDecimal amount;
    private String sender;
    private String recipient;
    private String message;
    private TransactionStatus status = TransactionStatus.PROCESSING;


    public Transaction(BigDecimal amount, String sender, String recipient, String message) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public Transaction(BigDecimal amount, String sender, String recipient) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
        this.message = "";
    }
}
