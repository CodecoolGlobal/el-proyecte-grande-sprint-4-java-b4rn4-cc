package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.*;
import com.codecool.bankapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public Optional<List<Transaction>> getHistory(@PathVariable UUID accountNumber) {
        return accountService.getHistoryByAccount(accountNumber);
    }

    @GetMapping("/user/{userID}")
    public Optional<List<Account>> getAccounts(@PathVariable UUID userID) {
        return accountService.getAccountsByUserID(userID);
    }

    @GetMapping("/user/{userID}/checking")
    public Optional<List<Account>> getCheckingAccounts(@PathVariable UUID userID) {
        return accountService.getAccountsByUserID(userID);
    }

    @PostMapping("/account/{userID}/add-{type}")
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

    @GetMapping("/user/{userID}/bills")
    public Optional<List<Bill>> getBills(@PathVariable UUID userID) {
        return accountService.getBillsByUserID(userID);
    }

    @GetMapping("/{accountNumber}/pay-bill/{billID}")
    public boolean payBill(@PathVariable UUID accountNumber, @PathVariable Long billID) {
        return accountService.payBillForUser(accountNumber, billID);
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
