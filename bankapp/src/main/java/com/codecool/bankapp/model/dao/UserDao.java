package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();
    Optional<User> findUser(String userID);   //TODO: change user parameter to unique ID
    void addUser(User newUser);
    boolean addAccount(String userID, Account account);
}
