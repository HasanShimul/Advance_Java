package com.example.e_commerce_backend.api;

import com.example.e_commerce_backend.domain.*;
import com.example.e_commerce_backend.repository.ProductRepository;
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
    @Autowired
    ProductRepository productrepository;

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
            Order savedorder =  buyerservice.order(order);
            StringBuilder message = new StringBuilder();
            message.append("Order successful.");
            for(OrderItem items : savedorder.getItems()){
                Product product = productrepository.findById(items.getProductId());
                    message.append("Produc:");
                    message.append(product.getProductName());
                    message.append(", Quantity:");
                    message.append(items.getQuantity());
                    message.append(", Total: ");
                    message.append(product.getProductPrice() * items.getQuantity());
                    message.append("\n");
            }

            message.append("Total Amount: ").append(savedorder.getTotalAmount());
    return message.toString();
        }else {
            return "Invalid token.";
        }

    }
}
