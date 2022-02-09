package com.codecool.bankapp.datasource;

import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.services.AccountService;
import com.codecool.bankapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Initializer {
    UserService userService;
    AccountService accountService;

    @Autowired
    public Initializer(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
        init();
    }

    private void init() {
        User user1 = new User("bandi", "12");
        User user2 = new User("adam", "123");
        BigDecimal money = new BigDecimal(100000);
        userService.addUser(user1);
        userService.addUser(user2);
        user1.getAccountList().get(0).depositMoney(money);
        user2.getAccountList().get(0).depositMoney(money);
        user1.getAccountList().get(0).setAccountNumber("1");
        user2.getAccountList().get(0).setAccountNumber("2");
        Transaction transaction1 = new Transaction(new BigDecimal(100), "1", "2");
        Transaction transaction2 = new Transaction(new BigDecimal(9999999), "1", "2");
        accountService.makeTransaction(transaction1);
        accountService.makeTransaction(transaction2);
    }
}
