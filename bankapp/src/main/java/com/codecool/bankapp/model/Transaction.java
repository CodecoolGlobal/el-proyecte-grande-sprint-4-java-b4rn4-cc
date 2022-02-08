package com.codecool.bankapp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class Transaction {
    private LocalDateTime transactionTime = LocalDateTime.now();
    private BigDecimal amount;
    private Account accountFrom;
    private Account recipient;
    private String message;

    @Autowired
    public Transaction() {
    }

    public Transaction(BigDecimal amount, Account accountFrom, Account recipient, String message) {
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.recipient = recipient;
        this.message = message;
    }

    public Transaction(BigDecimal amount, Account accountFrom, Account recipient) {
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.recipient = recipient;
        this.message = "";
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
