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
    UserRepository userRepository;
    AccountRepository accountRepository;

    @Autowired
    public Initializer(UserService userService, AccountService accountService, UserRepository userRepository, AccountRepository accountRepository) {
        this.userService = userService;
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        init();
        Configuration.setupApp();
    }

    private void init() {
        User bank = User.builder().name("Banco Grande Incorporated").password("123")
                .userID(UUID.fromString("99999999-9999-9999-9999-999999999999"))
                .address("1234 Budapest, Hungary, Hal Street 99.").build();
        Account bankFundAccount = Account.builder().accountNumber(UUID.fromString("00000000-0000-0000-0000-000000000000")).balance(new BigDecimal(900000000)).build();
        accountRepository.save(bankFundAccount);
        bank.addAccountToList(bankFundAccount);
        userRepository.save(bank);


//        User user1 = User.builder().name("Bandi").password("12")
//                .userID(UUID.fromString("11111111-2222-3333-4444-555555555555"))
//                .address("City of Westminster, Downing Street 10.").build();
//        User user2 = User.builder().name("Adam").password("123")
//                .address("1111 Budapest, Csirke utca 7.")
//                .userID(UUID.fromString("99999999-2222-3333-4444-555555555555")).build();
//
//        BigDecimal money = new BigDecimal(100000);
//        userService.addUser(user1);
//        userService.addUser(user2);
//        user1.getAccountList().get(0).setBalance(money);
//        user2.getAccountList().get(0).setBalance(money);
//        UUID account1 = UUID.fromString("cd601a0a-ff98-47fc-90a9-d5fe1b5fa26c");
//        UUID account2 = UUID.fromString("ba1ca0c3-3090-4daf-b684-0fb9a0564c71");
//        UUID account3 = UUID.fromString("00000000-2222-1111-0000-444444444444");
//        user1.getAccountList().get(0).setAccountNumber(account1);
//        user2.getAccountList().get(0).setAccountNumber(account2);
//        user2.getAccountList().get(0).setCurrency(CurrencyType.HUF);
//        CheckingAccount secondAccount = CheckingAccount.builder().userID(user1.getUserID()).accountNumber(account3).currency(CurrencyType.GBP).build();
//        accountService.addAccount(secondAccount, "checking");
//        secondAccount.setBalance(money);
//        user1.addAccountToList(secondAccount);
//        Transaction transaction1 = Transaction.builder().amount(new BigDecimal(100)).sender(user1.getAccountList().get(0)).currency(CurrencyType.EUR).recipient(user2.getAccountList().get(0)).build();
//        Transaction transaction2 = Transaction.builder().amount(new BigDecimal(9999999)).sender(user1.getAccountList().get(0)).currency(CurrencyType.EUR).recipient(user2.getAccountList().get(0)).build();
//        accountService.makeTransaction(transaction1);
//        accountService.makeTransaction(transaction2);
    }
}
