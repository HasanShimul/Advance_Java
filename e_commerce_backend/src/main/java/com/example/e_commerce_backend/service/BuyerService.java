package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.domain.Buyer;
import com.example.e_commerce_backend.domain.Order;
import com.example.e_commerce_backend.domain.OrderItem;
import com.example.e_commerce_backend.domain.Product;
import com.example.e_commerce_backend.enums.UserRole;
import com.example.e_commerce_backend.exception.InsufficientStockException;
import com.example.e_commerce_backend.exception.ProductNotFoundException;
import com.example.e_commerce_backend.repository.BuyerRepository;
import com.example.e_commerce_backend.repository.OrderRepository;
import com.example.e_commerce_backend.repository.ProductRepository;
import com.example.e_commerce_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

@Service
public class BuyerService {

    private final BuyerRepository buyerrepository;
    private final BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
    @Autowired
    private JwtUtil jwtutil;
    @Autowired
    ProductRepository productrepository;
    @Autowired
    OrderRepository orderrepository;

    public BuyerService(BuyerRepository buyerrepository){
        this.buyerrepository = buyerrepository;
    }
    public boolean signup(Buyer buyer){

        Buyer buyerexist = buyerrepository.checkBuyerByEmail(buyer.getEmail());
        if (buyerexist != null){
            throw new RuntimeException("Buhyer already exist " + buyer.getEmail());
        }

        String hashpassword = passwordencoder.encode(buyer.getPassword());
        buyer.setPassword(hashpassword);

        return buyerrepository.signup(buyer);
    }

    public String login(String email, String password){
        Buyer buyer = buyerrepository.checkBuyerByEmail(email);
        if (buyer == null){
            throw new RuntimeException("Seller doesn't exist.");
        }
        boolean isMatche = passwordencoder.matches(password,buyer.getPassword());
        if (!isMatche){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        String token = jwtutil.generateToken(buyer.getEmail(), UserRole.BUYER);
        System.out.println("token : " + token);
    return token;
    }

    @Transactional
    public Order order(Order order){
        double total = 0;
        for(OrderItem item : order.getItems()){
            Product product = productrepository.findById(item.getProductId());
            if (product == null){
                throw new ProductNotFoundException("Product not found" + item.getProductId());
            }
            if (product.getProductLimited() < item.getQuantity()){
                throw new InsufficientStockException("Not enough stock for product: " + product.getId());
            }
            total += product.getProductPrice() * item.getQuantity();

            product.setProductLimited(product.getProductLimited() - item.getQuantity());
            product.setProductSold(product.getProductSold() + item.getQuantity());
            productrepository.updateStock(product.getId(),item.getQuantity());
        }
        order.setTotalAmount(total);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalTime.now());
 return orderrepository.saveOrder(order);

     }
}
