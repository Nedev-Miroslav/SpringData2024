package com.example.w09springdataintro.services;

import com.example.w09springdataintro.models.User;

public interface UserService {
    void register(String username, int age);

    User findByUsername(String username);
}
