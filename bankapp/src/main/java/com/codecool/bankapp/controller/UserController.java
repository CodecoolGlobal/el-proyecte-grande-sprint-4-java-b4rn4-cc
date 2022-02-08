package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.model.User;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping
    public @ResponseBody User getUser() {
        return new User("bandi", "test");
    }

    @PostMapping("/register")
    public @ResponseBody User registerUser(@RequestBody User newUser) {
        users.add(newUser);
        return newUser;
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        return users;
    }

    @GetMapping("/test")
    public List<Transaction> test() {
        User user1 = new User("bandi", "12");
        User user2 = new User("adam", "123");
        Account account1 = user1.getAccountList().get(0);
        Account account2 = user2.getAccountList().get(0);
        Transaction transaction = new Transaction(new BigDecimal("0"), account1, account2);
        account1.addToHistory(transaction);
        account2.addToHistory(transaction);
        users.add(user1);
        users.add(user2);
        return user1.getAccountList().get(0).getHistory();
    }
}
