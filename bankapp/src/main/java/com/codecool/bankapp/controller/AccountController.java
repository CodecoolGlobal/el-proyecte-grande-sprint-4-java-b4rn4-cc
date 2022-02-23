package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/history")     // TODO: maybe add to url
    public List<Transaction> getHistory(@RequestParam UUID accountNumber) {
        return accountService.getHistoryByAccount(accountNumber);
    }

    @GetMapping("/list")
    public List<Account> getAccounts(@RequestParam UUID userID) {
        return accountService.getAccountsByUserID(userID);
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
