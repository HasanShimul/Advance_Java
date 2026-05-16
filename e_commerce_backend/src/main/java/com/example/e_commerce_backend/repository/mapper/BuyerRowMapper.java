package com.example.e_commerce_backend.repository.mapper;

import com.example.e_commerce_backend.domain.Buyer;
import com.example.e_commerce_backend.domain.BuyerLogin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyerRowMapper implements RowMapper<Buyer> {
    @Override
    public Buyer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Buyer buyer = new Buyer();
        buyer.setName(rs.getString("name"));
        buyer.setEmail(rs.getString("email"));
        buyer.setPassword(rs.getString("password"));
        buyer.setId(rs.getLong("id"));
        buyer.setPhone(rs.getString("phone"));
        buyer.setAddress(rs.getString("address"));

        return buyer;
    }
}
