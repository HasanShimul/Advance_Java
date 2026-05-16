package com.example.e_commerce_backend.repository.mapper;

import com.example.e_commerce_backend.domain.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product>{
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductPrice(rs.getDouble("product_price"));
        product.setProductLimited(rs.getInt("product_limited"));
        product.setProductSold(rs.getLong("product_sold"));
        product.setSellerId(rs.getLong("seller_id"));
        return product;
    }
}

