package com.example.e_commerce_backend.utility;

 import com.example.e_commerce_backend.enums.UserRole;
 import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
 import io.jsonwebtoken.security.Keys;
 import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private   Key SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public   String generateToken(String email , UserRole role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims extractToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return extractToken(token).getSubject();
    }
    public  String extractRole(String token){
        return extractToken(token).get("role",String.class);
    }

    public boolean checkTokenValid (String token){
        try{
            extractToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
