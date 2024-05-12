package com.example.w09springdataintro;

import com.example.w09springdataintro.models.User;
import com.example.w09springdataintro.services.AccountService;
import com.example.w09springdataintro.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        User user = this.userService.findByUsername("first");
        this.accountService
                .depositMoney(BigDecimal.TEN, user.getAccountIds().get(0));
        this.accountService
                .withdrawMoney(BigDecimal.ONE, user.getAccountIds().get(0));
    }
}