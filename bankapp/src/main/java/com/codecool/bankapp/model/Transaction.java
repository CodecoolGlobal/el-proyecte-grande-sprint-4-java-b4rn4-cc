package com.codecool.bankapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime transactionTime = LocalDateTime.now();
    private BigDecimal amount;
    private String sender;
    private String recipient;
    private String message;
    private TransactionStatus status = TransactionStatus.PROCESSING;

    public Transaction(){}

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

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
