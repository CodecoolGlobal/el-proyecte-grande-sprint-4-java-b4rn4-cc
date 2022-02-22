package com.codecool.bankapp.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class CheckingAccount extends Account {

    public void withdrawMoney(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
