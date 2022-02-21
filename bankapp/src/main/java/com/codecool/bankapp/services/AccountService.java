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
        Account sender = accountDao.findAccount(transaction.getSender()); //TODO: validate sender type first & avoid casting
        Account destination = accountDao.findAccount(transaction.getRecipient());;
        BigDecimal amount = transaction.getAmount();
        BigDecimal zero = BigDecimal.ZERO;

        if (amount.compareTo(zero) > 0 && sender instanceof CheckingAccount && amount.compareTo(sender.getBalance()) < 1) {
            ((CheckingAccount) sender).withdrawMoney(amount);
            destination.depositMoney(amount);
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
        } else {
            transaction.setStatus(TransactionStatus.REJECTED);
        }
        accountDao.addToHistory(sender, transaction);
        accountDao.addToHistory(destination, transaction);
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
