package com.example.e_commerce_backend.repository;

import com.example.e_commerce_backend.domain.Buyer;
import com.example.e_commerce_backend.repository.mapper.BuyerRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BuyerRepository {
    private  final NamedParameterJdbcTemplate namedparameterjdbctemplate;

    public BuyerRepository(NamedParameterJdbcTemplate namedparameterjdbctemplate){
        this.namedparameterjdbctemplate = namedparameterjdbctemplate;
    }

    public Buyer checkBuyerByEmail(String email){
        String sql = """
                SELECT * FROM buyers WHERE email = :email;
                """;
        Map<String,Object> params = new HashMap<>();
        params.put("email",email);
       return namedparameterjdbctemplate.queryForObject(sql,params,new BuyerRowMapper());

    }

    public boolean signup(Buyer buyer){
        String sql = """
                INSERT INTO buyers (name,email,password,phone,address) VALUES (:name, :email, :password, :phone, :address);
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",buyer.getName());
        params.addValue("email",buyer.getEmail());
        params.addValue("password",buyer.getPassword());
        params.addValue("phone",buyer.getPhone());
        params.addValue("address",buyer.getAddress());

        int row = namedparameterjdbctemplate.update(sql,params);
        return row >0 ;
    }
}
