package com.codecool.bankapp.datasource;

import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.services.AccountService;
import com.codecool.bankapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

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
        User user1 = User.builder().name("Bandi").password("12")
                .userID(UUID.fromString("11111111-2222-3333-4444-555555555555"))
                .address("City of Westminster, Downing Street 10.").build();
        User user2 = User.builder().name("Adam").password("123")
                .address("1111 Budapest, Csirke utca 7.")
                .userID(UUID.fromString("99999999-2222-3333-4444-555555555555")).build();
        User bank = User.builder().name("Banco Grande Incorporated").password("12")
                .userID(UUID.fromString("11111111-2222-3333-4444-555555555555"))
                .address("1234 Budapest, Hungary, Hal Street 99.").build();

        BigDecimal money = new BigDecimal(100000);
        userService.addUser(user1);
        userService.addUser(user2);
        user1.getAccountList().get(0).depositMoney(money);
        user2.getAccountList().get(0).depositMoney(money);
        bank.getAccountList().get(0).depositMoney(new BigDecimal(900000000));
        UUID account1 = UUID.fromString("cd601a0a-ff98-47fc-90a9-d5fe1b5fa26c");
        UUID account2 = UUID.fromString("ba1ca0c3-3090-4daf-b684-0fb9a0564c71");
        UUID bankFundAccount = UUID.fromString("00000000-0000-0000-0000-000000000000");
        user1.getAccountList().get(0).setAccountNumber(account1);
        user2.getAccountList().get(0).setAccountNumber(account2);
        bank.getAccountList().get(0).setAccountNumber(bankFundAccount);
        Transaction transaction1 = Transaction.builder().amount(new BigDecimal(100)).sender(account1).recipient(account2).build();
        Transaction transaction2 = Transaction.builder().amount(new BigDecimal(9999999)).sender(account1).recipient(account2).build();
        accountService.makeTransaction(transaction1);
        accountService.makeTransaction(transaction2);
    }
}
