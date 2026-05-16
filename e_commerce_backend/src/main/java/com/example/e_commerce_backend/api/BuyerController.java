package com.example.e_commerce_backend.api;

import com.example.e_commerce_backend.domain.Buyer;
import com.example.e_commerce_backend.domain.BuyerLogin;
import com.example.e_commerce_backend.domain.Order;
import com.example.e_commerce_backend.service.BuyerService;
import com.example.e_commerce_backend.utility.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    private final BuyerService buyerservice;
    @Autowired
    JwtUtil jwtutil;

    public BuyerController(BuyerService buyerservice){
        this.buyerservice = buyerservice;
    }

    @PostMapping("/signup")
    public Object signup(
           @Valid @RequestBody Buyer buyer,
           BindingResult result
            ){
        if (result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->{
                errors.put(error.getField(),error.getDefaultMessage());
            });
            return errors;
        }
        if( buyerservice.signup(buyer)){
            return buyer.getName() + " has been successfully added as buyer . ";
        }else {
            return buyer.getName() + " can not be added.";
        }

    }

    @PostMapping("/login")
    public String login(@RequestBody BuyerLogin login){

     return buyerservice.login(login.getEmail(), login.getPassword());
    }

    @PostMapping("/order")
    public Object orderItems(
            @RequestHeader(value = "Authorization" ,required = false) String authheader,
            @RequestBody Order order){
        if (authheader == null || !authheader.startsWith("Bearer ")){
            return "TOken is missing.";
        }
        String token =  authheader.substring(7);
        if (jwtutil.checkTokenValid(token)){
            return buyerservice.order(order);
        }else {
            return "Invalid token.";
        }



    }
}
