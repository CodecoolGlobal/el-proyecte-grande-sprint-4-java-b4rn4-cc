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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTests {

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

}
