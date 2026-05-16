package com.example.e_commerce_backend.api;

import com.example.e_commerce_backend.domain.SellerLogin;
import com.example.e_commerce_backend.domain.Product;
import com.example.e_commerce_backend.domain.Seller;
import com.example.e_commerce_backend.enums.UserRole;
import com.example.e_commerce_backend.service.SellerService;
import com.example.e_commerce_backend.utility.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/seller")
public class SellerController {

    private   final SellerService sellerservice;
    @Autowired
    private JwtUtil jwtutil;

    public SellerController(SellerService sellerservice){
        this.sellerservice = sellerservice;
    }

    @PostMapping("/signup")
    public Object signup(@Valid @RequestBody Seller seller , BindingResult result){

        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();

            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

            return errors;
        }
        boolean signupStatus =  sellerservice.singup(seller);
        if (signupStatus){
            return seller.getName() + " has been signup successfull with email " + seller.getEmail();
        }else {
            return "User already exist for " + seller.getEmail();
        }
    }

    @PostMapping("/login")
    public Map<String,String> login( @RequestBody SellerLogin login){

        System.out.println("email : " + login.getEmail());

        String token = sellerservice.login(login.getEmail(),login.getPassword());
        Map<String,String> response = new HashMap<>();
        response.put("token" , token);
        return response;
    }

    @PostMapping("/addproduct")
    public Object addProduct(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Product products
    ){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return "Token is missing.";
        }
        String token = authHeader.substring(7);
        if(!jwtutil.checkTokenValid(token)){
            return "Invalid token.";
        }
        String role = jwtutil.extractRole(token);
        UserRole userRole = UserRole.valueOf(role);
        if (!(userRole == UserRole.SELLER)){
            return "FOrbidden.";
        }

        //will implement product validation if error send to client else move forword

        String sellerEmail = jwtutil.extractEmail(token);
        boolean productInserted = sellerservice.addProduct(products,sellerEmail);
        if (productInserted){
            return  products.getProductName() + " is added by " + sellerEmail;
        }else {
            return products.getProductName() + " can't be added.";
        }

    }
}
