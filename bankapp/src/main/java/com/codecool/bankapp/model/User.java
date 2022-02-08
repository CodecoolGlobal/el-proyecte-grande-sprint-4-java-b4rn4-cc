package com.codecool.bankapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class User {
    private String name;
    private String address;
    private String password;
    private final List<Account> accountList = new ArrayList<>();

    @Autowired
    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        accountList.add(createAccount());
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addAccountToList(Account account) {
        accountList.add(account);
    }

    public Account createAccount() {
        // TODO: 2022. 02. 08. factory pattern for which type of account should be made
        return new CheckingAccount();
    }
}
