package com.codecool.bankapp.services;

import com.codecool.bankapp.model.CurrencyType;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;
    AccountService accountService;
    private LocalTime initTime = LocalTime.MIN;

    @Autowired
    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public User getUser(UUID userID) {
        return userRepository.findUserByUserIDEquals(userID).orElse(null);
    }

    @Transactional
    public void addUser(User newUser){
        if(newUser.getUserID() == null) {
            newUser.setUserID(UUID.randomUUID());
        }
        if(newUser.areNullFields()) {
            return;
        }
        userRepository.save(newUser);
        accountService.addAccount(newUser.getUserID(), "checking", CurrencyType.EUR);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
