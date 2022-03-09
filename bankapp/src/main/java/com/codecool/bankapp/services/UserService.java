package com.codecool.bankapp.services;

import com.codecool.bankapp.model.CheckingAccount;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;
    AccountService accountService;

    @Autowired
    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public User getUser(UUID userID) {
        return userRepository.findUserByUserIDEquals(userID).orElse(null);
    }

    public void addUser(User newUser){
        userDao.addUser(newUser);
        CheckingAccount checkingAccount = CheckingAccount.builder().userID(newUser.getUserID()).build();
        userDao.addAccount(newUser.getUserID(), checkingAccount);
        accountDao.addCheckingAccount(checkingAccount);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
