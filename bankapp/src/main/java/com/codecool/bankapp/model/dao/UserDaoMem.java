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
    public User findUser(User user) {
        for (User userResult : users) {
            if (user.equals(userResult)) {
                return userResult;
            }
        }
        return null;
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    @Override
    public void addAccount(User user, Account account) {
        for (User userResult : users) {
            if (user.equals(userResult)) {
                userResult.addAccountToList(account);
            }
        }
    }
}
