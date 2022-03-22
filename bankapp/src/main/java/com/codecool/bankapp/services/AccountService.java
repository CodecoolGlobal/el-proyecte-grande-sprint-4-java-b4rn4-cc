package com.codecool.bankapp.services;

import com.codecool.bankapp.datasource.Configuration;
import com.codecool.bankapp.model.*;
import com.codecool.bankapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@Service
public class AccountService {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    UserRepository userRepository;
    private final CurrencyRatesRepository ratesRepository;
    private final RateRepository rateRepository;
    private final BillRepository billRepository;
    private Properties props;
    private String apiKey;
    private final RestTemplate template = new RestTemplate();


    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository, CurrencyRatesRepository ratesRepository, RateRepository rateRepository, BillRepository billRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.ratesRepository = ratesRepository;
        this.rateRepository = rateRepository;
        this.billRepository = billRepository;
    }

    @Transactional
    public Transaction makeTransaction(Transaction transaction) {
        CheckingAccount sender = accountRepository.findCheckingAccountByAccountNumberEquals(transaction.getSender().getAccountNumber()).orElse(null);
        Account destination = accountRepository.findAccountByAccountNumberEquals(transaction.getRecipient().getAccountNumber()).orElse(null);
        BigDecimal amount = transaction.getAmount();
        BigDecimal zero = BigDecimal.ZERO;

        if(sender != null && destination != null) {
            if (amount.compareTo(zero) > 0 && amount.compareTo(sender.getBalance()) < 1) {
                if(sender.withdrawMoney(amount)) {
                    CurrencyType targetCurrency = destination.getCurrency();
                    BigDecimal depositedMoney = exchangeCurrency(amount, transaction.getCurrency(), targetCurrency);
                    destination.depositMoney(depositedMoney);
                    transaction.setStatus(TransactionStatus.SUCCESSFUL);
                } else {
                    transaction.setStatus(TransactionStatus.REJECTED);
                }
            } else {
                transaction.setStatus(TransactionStatus.REJECTED);
            }
            transaction.setSender(sender);
            transaction.setRecipient(destination);
            transactionRepository.save(transaction);
            sender.addToHistory(transaction);
            destination.addToHistory(transaction);
            accountRepository.save(sender);
            accountRepository.save(destination);
            return transaction;
        }
        return null;
    }

    public Optional<List<Transaction>> getHistoryByAccount(UUID accountNumber) {
        var account = accountRepository.findAccountByAccountNumberEquals(accountNumber);

        return account.map(Account::getHistory);
    }

    private BigDecimal exchangeCurrency(BigDecimal amount, CurrencyType baseCurrency, CurrencyType targetCurrency) {
        CurrencyRates currencyRates = getCurrencyRates();
        if(baseCurrency.equals(targetCurrency)) {
            return amount;
        }
        BigDecimal rate;
        if(targetCurrency.equals(CurrencyType.EUR)) {
            rate = BigDecimal.ONE;
        } else {
            rate = currencyRates.getRateBysymbol(targetCurrency);
        }
        if(!baseCurrency.equals(CurrencyType.EUR)) {
            rate = rate.divide(currencyRates.getRateBysymbol(baseCurrency), 4, RoundingMode.HALF_UP);
        }
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    public Optional<List<Account>> getAccountsByUserID(UUID userID) {
        return accountRepository.getAccountsByUserIDEquals(userID);
    }

    public Optional<List<CheckingAccount>> getCheckingAccountsByUserID(UUID userID) {
        return accountRepository.getCheckingAccountsByUserIDEquals(userID);
    }

    @Transactional
    public void addAccount(UUID userID, String type, CurrencyType currency) {
        User user = userRepository.findUserByUserIDEquals(userID).orElse(null);
        if(user != null) {
            Account newAccount;
            if(type.equals("savings")) {
                newAccount = SavingsAccount.builder().userID(userID).currency(currency).build();
            } else if(type.equals("checking")) {
                newAccount = CheckingAccount.builder().userID(userID).currency(currency).build();
            } else {
                return;
            }
            accountRepository.save(newAccount);
            user.addAccountToList(newAccount);
            userRepository.save(user);
        }
    }

    public Transaction makeTransactionATM(Transaction transaction, String type) {
        UUID atmNumber = UUID.fromString("00000000-0000-0000-0000-000000000000");
        CheckingAccount bankFundAccount = accountRepository.findCheckingAccountByAccountNumberEquals(atmNumber).orElse(null);
        if(type.equals("deposit")) {
            transaction.setSender(bankFundAccount);
        } else if(type.equals("withdraw")) {
            transaction.setRecipient(bankFundAccount);
        } else {
            return null;
        }
        return makeTransaction(transaction);
    }

    public void saveCurrencies(CurrencyRates rates) {
        rateRepository.saveAll(rates.getRatesList());
        ratesRepository.save(rates);
    }

    public CurrencyRates getCurrencyRates() {
        if (props == null) {
            this.props = Configuration.getProps();
            this.apiKey = props.getProperty("apikey");
        }
        LocalDate today = LocalDate.now();
        CurrencyRates currencyRates = ratesRepository.findFirstByOrderByIdDesc();

        if (currencyRates == null || today.compareTo(LocalDate.ofInstant(currencyRates.getDate().toInstant(), ZoneId.systemDefault())) > 0) {
            String url = "http://data.fixer.io/api/latest?access_key=" + apiKey + "&symbols=GBP,JPY,USD,HUF";
            CurrencyRates currency = template.getForObject(url, CurrencyRates.class);
            assert currency != null;
            currency.unpackRates(currency.getRates());
            saveCurrencies(currency);
            return currency;
        }
        currencyRates.packRates(currencyRates.getRatesList());
        return currencyRates;
    }

    public Optional<List<Bill>> getBillsByUserID(UUID userID) {
        return billRepository.findBillsByPayerUserIDEquals(userID);
    }

    public boolean payBillForUser(UUID accountNumber, Long billID) {
        CheckingAccount sender = accountRepository.findCheckingAccountByAccountNumberEquals(accountNumber).orElse(null);
        Bill bill = billRepository.findById(billID).orElse(null);

        if(bill != null && sender != null) {
            Transaction dueTransaction = bill.getTransaction();
            Transaction cloneTransaction = new Transaction();
            cloneTransaction.cloneBillTransaction(dueTransaction);

            BigDecimal senderAmount = exchangeCurrency(dueTransaction.getAmount(), dueTransaction.getCurrency(), sender.getCurrency());
            dueTransaction.setCurrency(sender.getCurrency());
            dueTransaction.setAmount(senderAmount);
            dueTransaction.setSender(sender);
            makeTransaction(dueTransaction);

            if(dueTransaction.getStatus() == TransactionStatus.SUCCESSFUL) {
                bill.setPaid(true);
            } else {
                transactionRepository.save(cloneTransaction);
                bill.setTransaction(cloneTransaction);
            }
            billRepository.save(bill);
            return bill.isPaid();
        }
        return false;
    }
}
