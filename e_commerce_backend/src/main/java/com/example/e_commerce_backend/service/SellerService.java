package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.domain.Product;
import com.example.e_commerce_backend.domain.Seller;
import com.example.e_commerce_backend.repository.SellerRepository;
import com.example.e_commerce_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.e_commerce_backend.enums.UserRole;
@Service
public class SellerService {

    private final SellerRepository sellerrepository;
    private final BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
    @Autowired
    private JwtUtil jwtutil;

    public SellerService(SellerRepository sellerrepository){
        this.sellerrepository = sellerrepository;
    }

    public boolean singup(Seller seller){
        boolean sellerExist = sellerrepository.checkSellerByEmail(seller.getEmail());
        if (sellerExist){
             return  false;
        }
        String hashPassword = passwordencoder.encode(seller.getPassword());
        System.out.println("Hash password : " + hashPassword);
        seller.setPassword(hashPassword);

        boolean saveStatus =  sellerrepository.signup(seller);
        return saveStatus;
    }

    public String login(String email, String password){
        Seller seller = sellerrepository.findByEmail(email);

        if(seller == null){
            throw new RuntimeException("Seller doesn't exist.");
        }
        boolean isMatched = passwordencoder.matches(password,seller.getPassword());
        if (!isMatched){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        System.out.println("Password : " + password);
        String token =  jwtutil.generateToken(seller.getEmail(),UserRole.SELLER);
        System.out.println("isMatched : " + isMatched);

        System.out.println("Hased : " + seller.getPassword());
        System.out.println("token : " + token);
        return token;
    }
    public boolean addProduct(Product products, String sellerEmail){

        return sellerrepository.addProduct(products,sellerEmail);
    }

}
