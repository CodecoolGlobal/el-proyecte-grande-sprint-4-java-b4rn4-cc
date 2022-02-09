package com.codecool.bankapp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(address, user.address) && Objects.equals(password, user.password) && Objects.equals(accountList, user.accountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, password, accountList);
    }
}
