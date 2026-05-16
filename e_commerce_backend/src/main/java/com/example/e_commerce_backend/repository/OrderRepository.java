package com.example.e_commerce_backend.repository;

import com.example.e_commerce_backend.domain.Order;
import com.example.e_commerce_backend.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    @Autowired
    NamedParameterJdbcTemplate namedjdbctemplate;

    public Order saveOrder(Order order){
        String sql = """
                INSERT INTO orders (buyer_id, total_amount, status, created_at)
                VALUES (:buyerId, :totalAmount, :status, :createdAt)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("buyerId", order.getBuyerId());
        params.addValue("totalAmount", order.getTotalAmount());
        params.addValue("status", order.getStatus());
        params.addValue("createdAt", order.getCreatedAt());

        KeyHolder keyholder = new GeneratedKeyHolder();
        namedjdbctemplate.update(sql,params,keyholder,new String[]{"id"});
        Long orderId = keyholder.getKey().longValue();

        String itemSql = """
                INSERT INTO order_items (order_id, product_id, quantity, price)
                VALUES (:orderId, :productId, :quantity, :price)
                """;
        for (OrderItem item : order.getItems()) {

            MapSqlParameterSource itemParams = new MapSqlParameterSource()
                    .addValue("orderId", orderId)
                    .addValue("productId", item.getProductId())
                    .addValue("quantity", item.getQuantity())
                    .addValue("price", item.getPrice()); // make sure this exists

            namedjdbctemplate.update(itemSql, itemParams);
        }
        order.setId(orderId);
        return order;
    }
}
