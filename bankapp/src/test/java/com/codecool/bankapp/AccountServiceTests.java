package com.codecool.bankapp;

import com.codecool.bankapp.controller.AccountController;
import com.codecool.bankapp.model.*;
import com.codecool.bankapp.services.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTests {

    private CurrencyRates buildTestCurrencyRates() {
        Rate rateGPB = new Rate(1L, CurrencyType.GBP, new BigDecimal("0.83"));
        Rate rateJPY = new Rate(1L, CurrencyType.JPY, new BigDecimal("126.28"));
        Rate rateUSD = new Rate(1L, CurrencyType.USD, new BigDecimal("1.09"));
        Rate rateHUF = new Rate(1L, CurrencyType.HUF, new BigDecimal("384.08"));
        CurrencyRates rates = new CurrencyRates();
        LocalDate date = LocalDate.now();
        rates.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        rates.setRatesList(List.of(rateGPB, rateJPY, rateUSD, rateHUF));
        return rates;
    }

    private Account buildCheckingAccount(BigDecimal accBalance, CurrencyType currencyType) {
        return CheckingAccount.builder()
                .accountNumber(UUID.randomUUID())
                .balance(accBalance)
                .currency(currencyType)
                .history(new ArrayList<>())
                .build();
    }

    @Autowired
    private AccountService service;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CurrencyRatesRepository currencyRatesRepository;
    @MockBean
    private RateRepository rateRepository;

    @Autowired
    private AccountController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @DisplayName("Test getHistoryByAccountNumber success")
    void testGetHistoryByAccountNum() {
        // Setting up mock
        UUID accNumber = UUID.randomUUID();
        Transaction transaction1 = new Transaction(1L, LocalDateTime.now(), new BigDecimal("100"),
                CurrencyType.EUR, new CheckingAccount(), new SavingsAccount(), "", TransactionStatus.SUCCESSFUL);
        CheckingAccount account = new CheckingAccount();
        account.addToHistory(transaction1);
        when(accountRepository.findAccountByAccountNumberEquals(accNumber)).thenReturn(Optional.of(account));

        // Execute service call
        var returnedTransactions = service.getHistoryByAccount(accNumber);

        assertEquals(Optional.of(List.of(transaction1)), returnedTransactions);
    }

    @Test
    @DisplayName("Test getHistoryByAccountNumber couldn't find account return null")
    void testGetHistoryByAccNumReturnsNull() {
        // mock repository
        UUID accNum = UUID.randomUUID();
        when(accountRepository.findAccountByAccountNumberEquals(accNum)).thenReturn(Optional.empty());

        // execute service call
        var transactions = service.getHistoryByAccount(accNum);

        assertEquals(Optional.of(transactions), Optional.of(Optional.empty()));
    }

    @Test
    @DisplayName("Test makeTransaction successful between same currency accounts")
    void testMakeTransactionReturnsSuccessfulTransaction() {
        CheckingAccount sender = (CheckingAccount) buildCheckingAccount(new BigDecimal("100"), CurrencyType.EUR);
        CheckingAccount recipient = (CheckingAccount) buildCheckingAccount(BigDecimal.ZERO, CurrencyType.EUR);
        BigDecimal transactionAmount = sender.getBalance();

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .amount(transactionAmount)
                .currency(sender.getCurrency())
                .sender(sender)
                .recipient(recipient)
                .build();

        CurrencyRates rates = buildTestCurrencyRates();

        when(currencyRatesRepository.findFirstByOrderByIdDesc()).thenReturn(rates);
        when(accountRepository.findAccountByAccountNumberEquals(sender.getAccountNumber())).thenReturn(Optional.of(sender));
        when(accountRepository.findAccountByAccountNumberEquals(recipient.getAccountNumber())).thenReturn(Optional.of(recipient));
        when(accountRepository.save(sender)).thenReturn(sender);
        when(accountRepository.save(recipient)).thenReturn(recipient);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction returnedTransaction = service.makeTransaction(transaction);

        // senders money decreased
        assertEquals(returnedTransaction.getSender().getBalance(), BigDecimal.ZERO);
        // recipient money increased
        assertEquals(returnedTransaction.getRecipient().getBalance(), transactionAmount);
        // successful transaction
        assertEquals(returnedTransaction.getStatus(), TransactionStatus.SUCCESSFUL);

    }

}
