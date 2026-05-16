package com.example.e_commerce_backend.repository.mapper;

import com.example.e_commerce_backend.domain.Seller;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerRowMapper implements RowMapper<Seller> {
    @Override
    public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getLong("id"));
        seller.setEmail(rs.getString("email"));
        seller.setName(rs.getString("name"));
        seller.setPassword(rs.getString("password"));
        seller.setPhone(rs.getString("phone"));

        return seller;
    }
}
