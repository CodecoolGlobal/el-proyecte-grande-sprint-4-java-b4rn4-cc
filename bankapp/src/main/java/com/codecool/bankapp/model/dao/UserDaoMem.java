package com.codecool.bankapp.model.dao;

import com.codecool.bankapp.model.Account;
import com.codecool.bankapp.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoMem implements UserDao {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User findUser(String userID) {
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
    public void addAccount(String userID, Account account) {
        User userResult = findUser(userID);
        if(userResult != null) {
            userResult.addAccountToList(account);
        }
    }
}
