package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Data
@SuperBuilder
public abstract class Account {
    protected String accountNumber;
    protected String userID;
    @Builder.Default
    protected BigDecimal balance = new BigDecimal("0");
    protected boolean canWithdraw;
    @JsonBackReference
    protected final List<Transaction> history = new ArrayList<>();

    @Autowired
    public Account() {
        this.accountNumber = UUID.randomUUID().toString();
    }

    public void depositMoney(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public boolean withdrawMoney(BigDecimal amount) {
        if(canWithdraw) {
            this.balance = this.balance.subtract(amount);
        }
        return canWithdraw;
    }

    public void addToHistory(Transaction transaction) {
        history.add(transaction);
    }
}

