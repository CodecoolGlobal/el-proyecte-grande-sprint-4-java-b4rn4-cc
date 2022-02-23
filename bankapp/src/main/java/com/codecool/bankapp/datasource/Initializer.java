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
        Configuration.setupApp();
    }

    private void init() {
        User user1 = User.builder().name("bandi").password("12").build();
        User user2 = User.builder().name("adam").password("123").build();
        BigDecimal money = new BigDecimal(100000);
        userService.addUser(user1);
        userService.addUser(user2);
        user1.getAccountList().get(0).depositMoney(money);
        user2.getAccountList().get(0).depositMoney(money);
        user1.getAccountList().get(0).setAccountNumber("1");
        user2.getAccountList().get(0).setAccountNumber("2");
        Transaction transaction1 = Transaction.builder().amount(new BigDecimal(100)).sender("1").recipient("2").build();
        Transaction transaction2 = Transaction.builder().amount(new BigDecimal(9999999)).sender("1").recipient("2").build();
        accountService.makeTransaction(transaction1);
        accountService.makeTransaction(transaction2);
    }
}
