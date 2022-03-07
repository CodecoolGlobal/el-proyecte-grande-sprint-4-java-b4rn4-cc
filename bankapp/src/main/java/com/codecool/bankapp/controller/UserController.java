package com.codecool.bankapp.controller;

import com.codecool.bankapp.datasource.Configuration;
import com.codecool.bankapp.model.Transaction;
import com.codecool.bankapp.model.User;
import com.codecool.bankapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin
public class UserController {
    private boolean newsFetched = false;
    private Object news;
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userID}")
    public @ResponseBody User getUser(@PathVariable UUID userID) {
        return userService.getUser(userID);
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

    @GetMapping("/news")
    public Object getNews() {
        Properties props = Configuration.getProps();
        String newsApiKey = props.getProperty("newsApiKey");
        RestTemplate template = new RestTemplate();
        if (!newsFetched) {
            String url = "https://newsdata.io/api/1/news?apikey="+ newsApiKey +"&language=en&category=business";
            news = template.getForObject(url, Object.class);
            newsFetched = true;
            return news;
        }
        return news;
    }
}
