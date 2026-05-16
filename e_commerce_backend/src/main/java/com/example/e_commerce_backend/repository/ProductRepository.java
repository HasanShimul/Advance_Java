package com.example.e_commerce_backend.repository;

import com.example.e_commerce_backend.domain.Product;
import com.example.e_commerce_backend.repository.mapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    @Autowired
    NamedParameterJdbcTemplate namedparameterjdbc;

    public Product checkProductByName(String name){
        String sql = """
                SELECT * FROM products WHERE product_name = :name;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",name);
       return namedparameterjdbc.queryForObject(sql,params,new ProductRowMapper());
    }

    public Product findById(Long id) {
        String sql = """
            SELECT * FROM products WHERE id = :id
            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return namedparameterjdbc.queryForObject(sql, params, new ProductRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateStock(Long id, int quantity){
        String sql = """
            UPDATE products
            SET product_limited = product_limited - :qty,
                product_sold = product_sold + :qty
            WHERE id = :id
            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("qty", quantity);

        namedparameterjdbc.update(sql, params);
    }

    public void update(Product product){
        String sql = """
            UPDATE products
            SET product_name = :name,product_price = :price,product_limited = :limited,
                product_sold = :sold,seller_id = :sellerId
            WHERE id = :id
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", product.getId());
        params.addValue("name", product.getProductName());
        params.addValue("price", product.getProductPrice());
        params.addValue("limited", product.getProductLimited());
        params.addValue("sold", product.getProductSold());
        params.addValue("sellerId", product.getSellerId());

        namedparameterjdbc.update(sql, params);
    }

}
