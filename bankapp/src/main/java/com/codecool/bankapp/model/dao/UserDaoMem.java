package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoMem implements UserDao {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> findUser(String userID) {
        return users.stream()
                .filter(u -> u.getUserID().equals(userID))
                .findFirst();
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    @Override
    public void addAccount(String userID, Account account) {
        findUser(userID).ifPresent(userResult -> userResult.addAccountToList(account));
    }
}
