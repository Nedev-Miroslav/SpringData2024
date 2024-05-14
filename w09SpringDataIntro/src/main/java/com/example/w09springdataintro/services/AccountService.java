package com.example.w09springdataintro.services;


import jakarta.transaction.Transactional;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal amount, Long id);
    void depositMoney(BigDecimal amount, Long id);

    @Transactional
    void transferMoney(Long accountFrom, Long accountTo, BigDecimal amount);
}
