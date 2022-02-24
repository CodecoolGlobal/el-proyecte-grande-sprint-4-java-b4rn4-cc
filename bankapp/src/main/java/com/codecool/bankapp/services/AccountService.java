package com.codecool.bankapp.services;

import com.codecool.bankapp.model.*;
import com.codecool.bankapp.model.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Component
public class AccountService {
    AccountDao accountDao;


    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Transaction makeTransaction(Transaction transaction, CurrencyRates currency) {
        Account sender = accountDao.findAccount(transaction.getSender());
        Account destination = accountDao.findAccount(transaction.getRecipient());
        BigDecimal amount = transaction.getAmount();
        BigDecimal zero = BigDecimal.ZERO;

        if (amount.compareTo(zero) > 0 && amount.compareTo(sender.getBalance()) < 1) {
            if(sender.withdrawMoney(amount)) {
                CurrencyType targetCurrency = destination.getCurrency();
                BigDecimal depositedMoney = exchangeCurrency(amount, transaction.getCurrency(), targetCurrency, currency);
                destination.depositMoney(depositedMoney);
                transaction.setStatus(TransactionStatus.SUCCESSFUL);
            } else {
                transaction.setStatus(TransactionStatus.REJECTED);
            }
        } else {
            transaction.setStatus(TransactionStatus.REJECTED);
        }

        accountDao.addToHistory(sender, transaction);
        accountDao.addToHistory(destination, transaction);
        return transaction;
    }

    public List<Transaction> getHistoryByAccount(UUID accountNumber) {
        Account account = accountDao.findAccount(accountNumber);
        return accountDao.getHistory(account);
    }

    private BigDecimal exchangeCurrency(BigDecimal amount, CurrencyType baseCurrency, CurrencyType targetCurrency, CurrencyRates currencies) {
        if(currencies == null) {
            return amount;
        }
        BigDecimal rate = currencies.getRates().get(targetCurrency);
        if(!baseCurrency.equals(CurrencyType.EUR)) {
            rate = rate.divide(currencies.getRates().get(baseCurrency), 2, RoundingMode.HALF_UP);
        }
        return amount.multiply(rate);
    }

    public List<Account> getAccountsByUserID(UUID userID) {
        return accountDao.getAccountsByUserID(userID);
    }
}
