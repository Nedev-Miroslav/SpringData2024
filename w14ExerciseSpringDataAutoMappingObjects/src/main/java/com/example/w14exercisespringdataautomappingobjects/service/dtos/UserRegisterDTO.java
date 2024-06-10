package com.example.w14exercisespringdataautomappingobjects.service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    @Email
    @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "Email is not matching the pattern.")
    private String email;
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*", message = "Password doesn't matches the given pattern")
    @Size(min = 6, message = "Password need to be at least 6 symbols")
    private String password;
    private String confirmPassword;
    private String fullName;


    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }
}
