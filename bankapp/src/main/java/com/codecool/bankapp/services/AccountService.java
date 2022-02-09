package com.codecool.bankapp.services;

import com.codecool.bankapp.model.*;
import com.codecool.bankapp.model.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountService {
    AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Transaction makeTransaction(Transaction transaction) {
        Account from = accountDao.findAccount(transaction.getSender());
        Account to = accountDao.findAccount(transaction.getRecipient());;
        BigDecimal amount = transaction.getAmount();
        BigDecimal zero = BigDecimal.ZERO;

        if (amount.compareTo(zero) > 0 && from instanceof CheckingAccount && amount.compareTo(from.getBalance()) < 1) {
            ((CheckingAccount) from).withdrawMoney(amount);
            to.depositMoney(amount);
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
        } else {
            transaction.setStatus(TransactionStatus.REJECTED);
        }
        accountDao.AddToHistory(from, transaction);
        accountDao.AddToHistory(to, transaction);
        return transaction;
    }

    public List<Transaction> getHistoryByAccount(String accountNumber) {
        Account account = accountDao.findAccount(accountNumber);
        return accountDao.getHistory(account);
    }

    public List<Account> getAccountsByUser(User user) {
        return accountDao.getAccountsByUser(user);
    }
}
