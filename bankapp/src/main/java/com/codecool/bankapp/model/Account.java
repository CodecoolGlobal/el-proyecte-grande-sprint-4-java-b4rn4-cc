package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public abstract class Account {
    private String accountNumber;
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

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(balance, account.balance) && Objects.equals(history, account.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, history);
    }
}

