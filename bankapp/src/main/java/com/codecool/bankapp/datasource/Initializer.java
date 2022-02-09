package com.codecool.bankapp.datasource;

import com.codecool.bankapp.model.User;
import com.codecool.bankapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Initializer {
    UserService userService;

    @Autowired
    public Initializer(UserService userService) {
        this.userService = userService;
        init();
    }

    private void init() {
        User user1 = new User("bandi", "12");
        User user2 = new User("adam", "123");
        BigDecimal money = new BigDecimal(1000000);
        userService.addUser(user1);
        userService.addUser(user2);
        user1.getAccountList().get(0).depositMoney(money);
        user2.getAccountList().get(0).depositMoney(money);
        user1.getAccountList().get(0).setAccountNumber("1");
        user2.getAccountList().get(0).setAccountNumber("2");
    }
}
