package com.codecool.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Builder.Default
    private UUID userID = UUID.randomUUID();
    private String name;
    private String address;
    private String password;       // TODO: move login credentials to separate object
    private final List<Account> accountList = new ArrayList<>();

    public void addAccountToList(Account account) {
        accountList.add(account);
    }
}
