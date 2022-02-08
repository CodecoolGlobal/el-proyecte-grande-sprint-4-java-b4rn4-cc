package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.CheckingAccount;
import com.codecool.bankapp.model.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/history")
    public List<Transaction> getHistory(@RequestBody Account account) {
        return account.getHistory();
    }

    @PostMapping("/transaction")
    public Transaction makeTransaction(@RequestBody Transaction transaction) {
        // TODO: 2022. 02. 08. move this into the service layer
        Account from = transaction.getAccountFrom();
        Account to = transaction.getRecipient();
        from.addToHistory(transaction);
        to.addToHistory(transaction);
        return transaction;
    }

    @PostMapping("/ATM-deposit")
    public Transaction makeDeposit(@RequestBody Transaction depositTransaction) {
        // TODO: 2022. 02. 08. move it into the service
        CheckingAccount from = (CheckingAccount) depositTransaction.getAccountFrom();
        CheckingAccount to = (CheckingAccount) depositTransaction.getRecipient();
        from.withdrawMoney(depositTransaction.getAmount());
        to.depositMoney(depositTransaction.getAmount());
        return depositTransaction;
    }
}
