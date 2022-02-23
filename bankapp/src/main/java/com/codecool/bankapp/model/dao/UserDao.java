package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    List<User> getAllUsers();
    Optional<User> findUser(UUID userID);
    void addUser(User newUser);
    boolean addAccount(UUID userID, Account account);
}
