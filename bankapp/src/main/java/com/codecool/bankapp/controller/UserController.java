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

@RestController
@RequestMapping("/")
@CrossOrigin
public class UserController {
    private boolean currencyFetched = false;
    private boolean newsFetched = false;
    private Object currency;
    private Object news;
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody User getUser() {
        return User.builder().name("bandi").password("test").build();
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

    @GetMapping("/main")
    public Object getCurrencies() {
        Properties props = Configuration.getProps();
        String apiKey = props.getProperty("apikey");
        RestTemplate template = new RestTemplate();
        if (!currencyFetched) {
            String url = "http://data.fixer.io/api/latest?access_key=" + apiKey + "&symbols=GBP,JPY,USD,HUF";
            currency = template.getForObject(url, Object.class);
            currencyFetched = true;
            return currency;
        }
        return currency;
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
