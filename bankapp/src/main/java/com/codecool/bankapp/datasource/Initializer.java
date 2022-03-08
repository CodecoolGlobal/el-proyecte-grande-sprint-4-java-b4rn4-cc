package com.codecool.bankapp.datasource;

import com.codecool.bankapp.model.*;
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
//        init();
        Configuration.setupApp();
    }

    private void init() {
        User user1 = User.builder().name("Bandi").password("12")
                .userID(UUID.fromString("11111111-2222-3333-4444-555555555555"))
                .address("City of Westminster, Downing Street 10.").build();
        User user2 = User.builder().name("Adam").password("123")
                .address("1111 Budapest, Csirke utca 7.")
                .userID(UUID.fromString("99999999-2222-3333-4444-555555555555")).build();
        User bank = User.builder().name("Banco Grande Incorporated").password("12")
                .userID(UUID.fromString("99999999-9999-9999-9999-999999999999"))
                .address("1234 Budapest, Hungary, Hal Street 99.").build();

        BigDecimal money = new BigDecimal(100000);
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(bank);
        user1.getAccountList().get(0).depositMoney(money);
        user2.getAccountList().get(0).depositMoney(money);
        bank.getAccountList().get(0).depositMoney(new BigDecimal(900000000));
        UUID account1 = UUID.fromString("cd601a0a-ff98-47fc-90a9-d5fe1b5fa26c");
        UUID account2 = UUID.fromString("ba1ca0c3-3090-4daf-b684-0fb9a0564c71");
        UUID account3 = UUID.fromString("00000000-2222-1111-0000-444444444444");
        UUID bankFundAccount = UUID.fromString("00000000-0000-0000-0000-000000000000");
        user1.getAccountList().get(0).setAccountNumber(account1);
        user2.getAccountList().get(0).setAccountNumber(account2);
        user2.getAccountList().get(0).setCurrency(CurrencyType.HUF);
        bank.getAccountList().get(0).setAccountNumber(bankFundAccount);
        CheckingAccount secondAccount = CheckingAccount.builder().userID(user1.getUserID()).accountNumber(account3).currency(CurrencyType.GBP).build();
//        accountService.addCheckingAccount(secondAccount);
        secondAccount.setBalance(money);
        user1.addAccountToList(secondAccount);
        Transaction transaction1 = Transaction.builder().amount(new BigDecimal(100)).sender(user1.getAccountList().get(0)).currency(CurrencyType.EUR).recipient(user2.getAccountList().get(0)).build();
        Transaction transaction2 = Transaction.builder().amount(new BigDecimal(9999999)).sender(user1.getAccountList().get(0)).currency(CurrencyType.EUR).recipient(user2.getAccountList().get(0)).build();
        CurrencyRates dummyCurrencyRates = new CurrencyRates();
        accountService.makeTransaction(transaction1, dummyCurrencyRates);
        accountService.makeTransaction(transaction2, dummyCurrencyRates);
    }
}
