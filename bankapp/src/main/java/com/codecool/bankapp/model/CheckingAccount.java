package com.codecool.bankapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class CheckingAccount extends Account {

    public void withdrawMoney(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
