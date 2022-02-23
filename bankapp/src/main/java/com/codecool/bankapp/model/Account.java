package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
public abstract class Account {
    @Builder.Default
    protected UUID accountNumber = UUID.randomUUID();;
    protected UUID userID;
    @Builder.Default
    protected BigDecimal balance = new BigDecimal("0");
    protected boolean canWithdraw;
    @JsonBackReference
    protected final List<Transaction> history = new ArrayList<>();

    public void depositMoney(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public boolean withdrawMoney(BigDecimal amount) {
        if(canWithdraw) {
            this.balance = balance.subtract(amount);
        }
        return canWithdraw;
    }

    public void addToHistory(Transaction transaction) {
        history.add(transaction);
    }
}

