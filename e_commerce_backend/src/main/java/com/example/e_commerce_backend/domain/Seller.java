package com.example.e_commerce_backend.domain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class Seller {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Need an email.")
    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^01[3-9]\\d{8}$",
             message = "Must be bangladeshi valid phone number")
    private String phone;

    @NotBlank(message = "Pasword can't be empty")
    @jakarta.validation.constraints.Size(min = 5, message = "Password must be at least 5 characters.")
    private String password;

    public Seller(){}

    public Seller(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
