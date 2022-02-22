package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    List<User> getAllUsers();
    User findUser(UUID userID);   //TODO: change user parameter to unique ID
    void addUser(User newUser);
    void addAccount(UUID userID, Account account);
}
