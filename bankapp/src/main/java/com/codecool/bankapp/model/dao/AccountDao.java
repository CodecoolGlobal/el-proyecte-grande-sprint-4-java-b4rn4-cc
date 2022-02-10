package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.*;

import java.util.List;

public interface AccountDao {
    Account findAccount(String accountNumber);
    List<Account> getAccountsByUser(User user);
    void addSavingsAccount(User user);
    List<Transaction> getHistory(Account account);
    void addToHistory(Account account, Transaction transaction);
    void addCheckingAccount(CheckingAccount account);
}
