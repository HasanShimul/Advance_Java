package com.example.e_commerce_backend.domain;

import java.time.LocalTime;
import java.util.List;

public class Order {
    private Long id;
    private Long buyerId;
    private String buyerEmail;
    private double totalAmount;
    private String status;
    private LocalTime createdAt;
    private List<OrderItem> items;

    public Order(){}

    public Order( String buyerEmail, double totalAmount, String status, LocalTime createdAt) {

        this.buyerEmail = buyerEmail;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;

    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
