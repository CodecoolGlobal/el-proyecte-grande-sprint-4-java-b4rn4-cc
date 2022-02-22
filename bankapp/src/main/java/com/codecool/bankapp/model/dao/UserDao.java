package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User findUser(String userID);   //TODO: change user parameter to unique ID
    void addUser(User newUser);
    void addAccount(String userID, Account account);
}
