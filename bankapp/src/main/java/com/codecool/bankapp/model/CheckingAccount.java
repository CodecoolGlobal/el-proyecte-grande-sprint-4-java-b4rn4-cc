package com.codecool.bankapp.model;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

    public void withdrawMoney(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
