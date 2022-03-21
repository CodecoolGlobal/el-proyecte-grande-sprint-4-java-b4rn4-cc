package com.codecool.bankapp.datasource;

import com.codecool.bankapp.model.*;
import com.codecool.bankapp.repository.AccountRepository;
import com.codecool.bankapp.repository.BillRepository;
import com.codecool.bankapp.repository.TransactionRepository;
import com.codecool.bankapp.repository.UserRepository;
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
    TransactionRepository transactionRepository;
    BillRepository billRepository;

    @Autowired
    public Initializer(UserService userService, AccountService accountService, UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, BillRepository billRepository) {
        this.userService = userService;
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.billRepository = billRepository;
        Configuration.setupApp();
        init();
    }

    private void init() {
        User bank = User.builder().name("Banco Grande Incorporated").password("123")
                .userID(UUID.fromString("99999999-9999-9999-9999-999999999999"))
                .address("1234 Budapest, Hungary, Hal Street 99.").build();
        Account bankFundAccount = CheckingAccount.builder().accountNumber(UUID.fromString("00000000-0000-0000-0000-000000000000")).balance(new BigDecimal(900000000)).build();
        initAccount(bank, bankFundAccount);

        User elmu = User.builder().name("ELMŰ-ÉMÁSZ Energiaszolgáltató ZRT.").password("123")
                .address("1132 Budapest, Váci út 72-74.").build();
        Account elmuAccount = CheckingAccount.builder().balance(new BigDecimal(10000)).build();
        initAccount(elmu, elmuAccount);

        BigDecimal money = new BigDecimal(100000);

        UUID user1ID = UUID.fromString("11111111-2222-3333-4444-555555555555");
        User user1 = User.builder().name("Bandi").password("12")
                .userID(user1ID)
                .address("City of Westminster, Downing Street 10.").build();
        Account account1 = CheckingAccount.builder().balance(money).currency(CurrencyType.EUR).userID(user1ID).build();
        Account account2 = CheckingAccount.builder().currency(CurrencyType.GBP).userID(user1ID).build();
        accountRepository.save(account1);
        user1.addAccountToList(account1);
        initAccount(user1, account2);

        UUID user2ID = UUID.fromString("99999999-2222-3333-4444-555555555555");
        User user2 = User.builder().name("Adam").password("123")
                .address("1111 Budapest, Csirke utca 7.")
                .userID(user2ID).build();
        Account account3 = CheckingAccount.builder().balance(money).currency(CurrencyType.HUF).userID(user2ID).build();
        initAccount(user2, account3);

        makeTransaction(new BigDecimal(100), account1, account3);
        makeTransaction(new BigDecimal(9999999), account1, account3);
        createBill(new BigDecimal(532), user1, elmu);
    }

    private void initAccount(User user, Account account) {
        accountRepository.save(account);
        user.addAccountToList(account);
        userRepository.save(user);
    }

    private void makeTransaction(BigDecimal amount, Account sender, Account recipient) {
        Transaction transaction = Transaction.builder().amount(amount).sender((CheckingAccount) sender).currency(sender.getCurrency()).recipient(recipient).build();
        accountService.makeTransaction(transaction);
    }

    private void createBill(BigDecimal amount, User payer, User recipient) {
        Bill bill = Bill.builder().payer(payer).recipient(recipient).build();
        Account recipientAccount = recipient.getAccountList().get(0);
        Transaction transaction = Transaction.builder().amount(amount).currency(recipientAccount.getCurrency()).recipient(recipientAccount).build();
        transactionRepository.save(transaction);
        bill.setTransaction(transaction);
        billRepository.save(bill);
    }
}
