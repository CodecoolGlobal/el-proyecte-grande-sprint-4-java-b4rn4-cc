package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDaoMem implements AccountDao {
    private final List<Account> accountList = new ArrayList<>();

    @Override
    public Account findAccount(String accountNumber) {
        for (Account account : accountList) {
            if(account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        return user.getAccountList();
    }

    @Override
    public void addSavingsAccount(User user) {
        SavingsAccount savingsAccount = new SavingsAccount();
        accountList.add(savingsAccount);
        user.addAccountToList(savingsAccount);
    }

    @Override
    public List<Transaction> getHistory(Account account) {
        return account.getHistory();
    }

    @Override
    public void addToHistory(Account account, Transaction transaction) {
        for (Account result : accountList) {
            if(result.equals(account)) {
                result.addToHistory(transaction);
            }
        }
    }

    @Override
    public void addCheckingAccount(CheckingAccount account) {
        accountList.add(account);
    }
}
