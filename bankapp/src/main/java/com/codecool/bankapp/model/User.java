package com.codecool.bankapp.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Data
@Builder
public class User {
    private UUID userID;
    private String name;
    private String address;
    private String password;       // TODO: move login credentials to separate object
    private final List<Account> accountList = new ArrayList<>();

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.userID = UUID.randomUUID();
    }

    public void addAccountToList(Account account) {
        accountList.add(account);
    }
}
