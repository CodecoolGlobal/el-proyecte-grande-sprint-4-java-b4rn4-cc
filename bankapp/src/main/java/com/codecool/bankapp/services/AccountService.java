package com.codecool.bankapp.services;

import com.codecool.bankapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    UserRepository userRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction makeTransaction(Transaction transaction, CurrencyRates currencyRates) {
        Account sender = transaction.getSender();
        Account destination = transaction.getRecipient();
        BigDecimal amount = transaction.getAmount();
        BigDecimal zero = BigDecimal.ZERO;

        if (amount.compareTo(zero) > 0 && amount.compareTo(sender.getBalance()) < 1) {
            if(sender.withdrawMoney(amount)) {
                CurrencyType targetCurrency = destination.getCurrency();
                BigDecimal depositedMoney = exchangeCurrency(amount, transaction.getCurrency(), targetCurrency, currencyRates);
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
        Account account = accountRepository.findAccountByAccountNumberEquals(accountNumber).orElse(null);
        if(account != null) {
            return account.getHistory();
        }
        return null;
    }

    private BigDecimal exchangeCurrency(BigDecimal amount, CurrencyType baseCurrency, CurrencyType targetCurrency, CurrencyRates currencyRates) {
        BigDecimal rate = currencyRates.getRateBysymbol(targetCurrency);
        if(!baseCurrency.equals(CurrencyType.EUR)) {
            rate = rate.divide(currencyRates.getRateBysymbol(baseCurrency), 2, RoundingMode.HALF_UP);
        }
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    public List<Account> getAccountsByUserID(UUID userID) {
        return accountRepository.getAccountsByUserIDEquals(userID);
    }

    @Transactional
    public void addCheckingAccount(UUID userID, CurrencyType currency) {
        User user = userRepository.findUserByUserIDEquals(userID).orElse(null);
        if(user != null) {
            CheckingAccount newAccount = CheckingAccount.builder().userID(userID).currency(currency).build();
            accountRepository.save(newAccount);
            user.addAccountToList(newAccount);
            userRepository.save(user);
        }
    }
}
