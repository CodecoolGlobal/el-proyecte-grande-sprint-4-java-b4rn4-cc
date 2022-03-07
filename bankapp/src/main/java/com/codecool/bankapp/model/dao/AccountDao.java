package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.*;

import java.util.List;
import java.util.UUID;

public interface AccountDao {
    Account findAccount(UUID accountNumber);
    List<Account> getAccountsByUserID(UUID userID);
    void addSavingsAccount(User user);
    List<Transaction> getHistory(Account account);
    void addToHistory(Account account, Transaction transaction);
    void addCheckingAccount(CheckingAccount account);
}
