package com.example.e_commerce_backend.domain;

import com.example.e_commerce_backend.repository.SellerRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Buyer {

    private Long id;

    @NotBlank(message = "Name can not be empty.")
    private String name;

    @NotBlank(message = "Must provide an email.")
    @Email(message = "Must be a valid email.")
    private String email;


    @NotBlank(message = "Pasword can't be empty")
    @jakarta.validation.constraints.Size(min = 5, message = "Password must be at least 5 characters.")
    String password;

    @NotBlank(message = "Must provide a phone number.")
    @Pattern(regexp = "^01[3-9]\\d{8}$",message = "need a valid bangladeshi phone number.")
    private String phone;

    @NotBlank(message = "Must need an address.")
    private String address;

    public Buyer(){}

    public Buyer(String name, String email, String password, String phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
