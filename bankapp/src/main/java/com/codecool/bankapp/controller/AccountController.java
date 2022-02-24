package com.codecool.bankapp.controller;

import com.codecool.bankapp.datasource.Configuration;
import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.CurrencyRates;
import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {
    private boolean currencyFetched = false;
    private CurrencyRates currency;
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}/history")
    public List<Transaction> getHistory(@PathVariable UUID accountNumber) {
        return accountService.getHistoryByAccount(accountNumber);
    }

    @GetMapping("/user/{userID}")
    public List<Account> getAccounts(@PathVariable UUID userID) {
        return accountService.getAccountsByUserID(userID);
    }

    @PostMapping("/transaction")
    public Transaction makeTransaction(@RequestBody Transaction transaction) {
        return accountService.makeTransaction(transaction, currency);
    }

    @PostMapping("/ATM-deposit")
    public Transaction makeDeposit(@RequestBody Transaction depositTransaction) {
        accountService.makeTransaction(depositTransaction, currency);
        return depositTransaction;
    }

    @GetMapping("/main")
    public CurrencyRates getCurrencies() {
        Properties props = Configuration.getProps();
        String apiKey = props.getProperty("apikey");
        RestTemplate template = new RestTemplate();
        if (!currencyFetched) {
            String url = "http://data.fixer.io/api/latest?access_key=" + apiKey + "&symbols=GBP,JPY,USD,HUF";
            currency = template.getForObject(url, CurrencyRates.class);
            currencyFetched = true;
            return currency;
        }
        return currency;
    }
}
