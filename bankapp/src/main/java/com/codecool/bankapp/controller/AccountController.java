package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.CurrencyRates;
import com.codecool.bankapp.model.CurrencyType;
import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
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

    @PostMapping("/user/{userID}/add-{type}")
    public void addAccount(@PathVariable("userID") UUID userID, @PathVariable("type") String type, @RequestBody CurrencyType currency) {
        accountService.addAccount(userID, type, currency);
    }

    @PostMapping("/transaction")
    public Transaction makeTransaction(@RequestBody Transaction transaction) {
        return accountService.makeTransaction(transaction);
    }

    @PostMapping("/ATM-{type}")
    public Transaction makeDeposit(@PathVariable("type") String type, @RequestBody Transaction transaction) {
        return accountService.makeTransactionATM(transaction, type);
    }

    @GetMapping("/main")
    public CurrencyRates getCurrencies() {
        return accountService.getCurrencyRates();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public void handleException() {
        System.out.println("JSON parse error: Cannot deserialize value(s)");
    }
}
