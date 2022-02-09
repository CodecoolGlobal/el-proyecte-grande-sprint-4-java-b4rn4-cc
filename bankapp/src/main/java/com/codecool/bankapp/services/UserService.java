package com.codecool.bankapp.services;

import com.codecool.bankapp.model.CheckingAccount;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.model.dao.AccountDao;
import com.codecool.bankapp.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserDao userDao;
    AccountDao accountDao;

    @Autowired
    public UserService(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    public void addUser(User newUser){
        userDao.addUser(newUser);
        CheckingAccount checkingAccount = new CheckingAccount();
        userDao.addAccount(newUser, checkingAccount);
        accountDao.addCheckingAccount(checkingAccount);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
