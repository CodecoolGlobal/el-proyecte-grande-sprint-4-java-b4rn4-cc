package com.codecool.bankapp.controller;

import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody User getUser() {
        return new User("bandi", "test");
    }

    @PostMapping("/register")
    public @ResponseBody User registerUser(@RequestBody User newUser) {
        userService.addUser(newUser);
        return newUser;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/test")
    public List<Transaction> test() {
        return null;
    }
}
