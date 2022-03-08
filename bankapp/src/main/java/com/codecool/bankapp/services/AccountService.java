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

    @Transactional
    public Transaction makeTransaction(Transaction transaction) {
        Account sender = accountRepository.findAccountByAccountNumberEquals(transaction.getSender().getAccountNumber()).orElse(null);
        Account destination = accountRepository.findAccountByAccountNumberEquals(transaction.getRecipient().getAccountNumber()).orElse(null);
        BigDecimal amount = transaction.getAmount();
        BigDecimal zero = BigDecimal.ZERO;

        if(sender != null && destination != null) {
            if (amount.compareTo(zero) > 0 && amount.compareTo(sender.getBalance()) < 1) {
                if(sender.withdrawMoney(amount)) {
                    CurrencyType targetCurrency = destination.getCurrency();
                    BigDecimal depositedMoney = exchangeCurrency(amount, transaction.getCurrency(), targetCurrency);
                    destination.depositMoney(depositedMoney);
                    transaction.setStatus(TransactionStatus.SUCCESSFUL);
                } else {
                    transaction.setStatus(TransactionStatus.REJECTED);
                }
            } else {
                transaction.setStatus(TransactionStatus.REJECTED);
            }
            transaction.setSender(sender);
            transaction.setRecipient(destination);
            transactionRepository.save(transaction);
            sender.addToHistory(transaction);
            destination.addToHistory(transaction);
            accountRepository.save(sender);
            accountRepository.save(destination);
            return transaction;
        }
        return null;
    }

    public List<Transaction> getHistoryByAccount(UUID accountNumber) {
        Account account = accountRepository.findAccountByAccountNumberEquals(accountNumber).orElse(null);
        if(account != null) {
            return account.getHistory();
        }
        return null;
    }

    private BigDecimal exchangeCurrency(BigDecimal amount, CurrencyType baseCurrency, CurrencyType targetCurrency) {
        CurrencyRates currencyRates = null; //TODO: get exchange rates here from db
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
    public void addAccount(UUID userID, String type, CurrencyType currency) {
        User user = userRepository.findUserByUserIDEquals(userID).orElse(null);
        if(user != null) {
            Account newAccount;
            if(type.equals("savings")) {
                newAccount = SavingsAccount.builder().userID(userID).currency(currency).build();
            } else if(type.equals("checking")) {
                newAccount = CheckingAccount.builder().userID(userID).currency(currency).build();
            } else {
                return;
            }
            accountRepository.save(newAccount);
            user.addAccountToList(newAccount);
            userRepository.save(user);
        }
    }

    public Transaction makeTransactionATM(Transaction transaction, String type) {
        UUID atmNumber = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Account bankFundAccount = accountRepository.findAccountByAccountNumberEquals(atmNumber).orElse(null);
        if(type.equals("deposit")) {
            transaction.setSender(bankFundAccount);
        } else if(type.equals("withdraw")) {
            transaction.setRecipient(bankFundAccount);
        } else {
            return null;
        }
        return makeTransaction(transaction);
    }
}
