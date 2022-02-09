package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/history")
    public List<Transaction> getHistory(@RequestParam(name = "accountNumber") String accountNumber) {
        return accountService.getHistoryByAccount(accountNumber);
    }

    @PostMapping
    public List<Account> getAccounts(@RequestBody User user) {
        return accountService.getAccountsByUser(user);
    }

    @PostMapping("/transaction")
    public Transaction makeTransaction(@RequestBody Transaction transaction) {
        return accountService.makeTransaction(transaction);
    }

    @PostMapping("/ATM-deposit")
    public Transaction makeDeposit(@RequestBody Transaction depositTransaction) {
        accountService.makeTransaction(depositTransaction);
        return depositTransaction;
    }
}
