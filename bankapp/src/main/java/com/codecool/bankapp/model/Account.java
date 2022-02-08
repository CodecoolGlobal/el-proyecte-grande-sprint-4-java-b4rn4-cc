package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public abstract class Account {
    private final String accountNumber;
    protected BigDecimal balance = new BigDecimal("0");
    @JsonBackReference
    protected final List<Transaction> history = new ArrayList<>();

    @Autowired
    public Account() {
        this.accountNumber = UUID.randomUUID().toString();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getHistory() {
        return history;
    }

    public void depositMoney(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void addToHistory(Transaction transaction) {
        history.add(transaction);
    }
}

