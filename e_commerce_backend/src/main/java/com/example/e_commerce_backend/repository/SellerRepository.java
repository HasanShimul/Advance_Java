package com.example.e_commerce_backend.repository;

import com.example.e_commerce_backend.domain.Product;
import com.example.e_commerce_backend.domain.Seller;
import com.example.e_commerce_backend.repository.mapper.SellerRowMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SellerRepository {

    private final NamedParameterJdbcTemplate namedparameterjdbctemplate;
    public SellerRepository(NamedParameterJdbcTemplate namedparameterjdbctemplate){
        this.namedparameterjdbctemplate = namedparameterjdbctemplate;
    }

    public boolean checkSellerByEmail(String email){
        String sql = """
                SELECT COUNT(*) FROM sellers WHERE email = :email;
                """;
        Map<String,Object> params = new HashMap<>();
        params.put("email",email);
        Integer count = namedparameterjdbctemplate.queryForObject(sql,params,Integer.class);

        return (count != null && count >0);

    }

    public boolean signup(Seller seller){
        String sql = """
                INSERT INTO sellers(name,email,phone,password) 
                VALUES (:name, :email, :phone, :password);
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",seller.getName());
        params.addValue("email",seller.getEmail());
        params.addValue("phone",seller.getPhone());
        params.addValue("password",seller.getPassword());
       int rowaffected =  namedparameterjdbctemplate.update(sql,params);
       return rowaffected > 0;

    }

    public Seller findByEmail(String email){
        String sql = """
                SELECT * FROM sellers WHERE email = :email;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource() ;
        params.addValue("email",email);

        try{
            return namedparameterjdbctemplate.queryForObject(sql,params,new SellerRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Long findsellerIdByEmail(String email){
        String sql = """
                SELECT id FROM sellers WHERE email = :email;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email",email);
        return  namedparameterjdbctemplate.queryForObject(sql,params, Long.class);
    }

    public boolean addProduct(Product products , String sellerEmail){
            Long sellerId = findsellerIdByEmail(sellerEmail);
            String sql = """
                    INSERT INTO products (product_name, product_price, product_limited, product_sold, seller_id)
                    VALUES (:name, :price, :limited, :sold, :sellerId)
                    """;
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name",products.getProductName());
            params.addValue("price",products.getProductPrice());
            params.addValue("limited",products.getProductLimited());
            params.addValue("sold",products.getProductSold());
            params.addValue("sellerId",sellerId);

             int row = namedparameterjdbctemplate.update(sql,params);
             return (row >0) ;
    }

}
