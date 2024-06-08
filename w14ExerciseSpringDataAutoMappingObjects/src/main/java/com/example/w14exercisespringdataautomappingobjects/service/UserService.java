package com.example.w14exercisespringdataautomappingobjects.service;

import com.example.w14exercisespringdataautomappingobjects.data.entities.User;
import com.example.w14exercisespringdataautomappingobjects.service.dtos.UserLoginDTO;
import com.example.w14exercisespringdataautomappingobjects.service.dtos.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logout();

    User getLoggedIn();
}

