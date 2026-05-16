package com.example.e_commerce_backend.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class BuyerLogin {
    @NotBlank(message = "Must provide an email")
    @Email(message = "Must be a valid email.")
    private String email;

    @NotBlank(message = "Must need a password.")
    @jakarta.validation.constraints.Size(min = 5, message = "Password must be at least 5 characters.")
    private String password;

    public BuyerLogin(){}

    public BuyerLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
