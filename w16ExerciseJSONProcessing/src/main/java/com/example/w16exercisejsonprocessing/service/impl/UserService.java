package com.example.w16exercisejsonprocessing.service.impl;

import com.example.w16exercisejsonprocessing.service.dtos.export.UserAndProductDto;
import com.example.w16exercisejsonprocessing.service.dtos.export.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;

    List<UserSoldProductsDto> getAllUsersAndSoldItems();
    void printAllUsersAndSoldItems();

    UserAndProductDto getUserAndProductDto();

    void printGetUserAndProductDto();

}
