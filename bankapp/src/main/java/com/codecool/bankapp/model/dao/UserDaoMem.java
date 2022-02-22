package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDaoMem implements UserDao {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User findUser(UUID userID) {
        for (User userResult : users) {
            if (userResult.getUserID().equals(userID)) {
                return userResult;
            }
        }
        return null;
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    @Override
    public void addAccount(UUID userID, Account account) {
        User userResult = findUser(userID);
        if(userResult != null) {
            userResult.addAccountToList(account);
        }
    }
}
