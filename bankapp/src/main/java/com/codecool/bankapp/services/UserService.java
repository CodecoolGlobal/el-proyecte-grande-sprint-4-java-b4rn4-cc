package com.codecool.bankapp.services;

import com.codecool.bankapp.model.CheckingAccount;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.model.dao.AccountDao;
import com.codecool.bankapp.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    UserDao userDao;
    AccountDao accountDao;
    private LocalTime initTime = LocalTime.MIN;

    @Autowired
    public UserService(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    public User getUser(UUID userID) {
        return userDao.findUser(userID).orElse(null);
    }

    public void addUser(User newUser){
        userDao.addUser(newUser);
        CheckingAccount checkingAccount = CheckingAccount.builder().userID(newUser.getUserID()).build();
        userDao.addAccount(newUser.getUserID(), checkingAccount);
        accountDao.addCheckingAccount(checkingAccount);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean shouldFetchNews(){
        LocalTime currentTime = LocalTime.now();
        if (currentTime.getHour() > initTime.getHour()) {
            initTime = currentTime;
            return true;
        }
        return false;
    }
}
