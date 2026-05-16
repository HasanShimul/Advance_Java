package com.example.e_commerce_backend.domain;

public class Product {
    private Long id;
    private String productName;
    private double productPrice;
    private int productLimited;
    private Long productSold;
    private Long sellerId;

    public Product(){}

    public Product(Long id, String productName, double productPrice, int productLimited, Long productSold) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productLimited = productLimited;
        this.productSold = productSold;
    }

    public String getProductName() {return productName;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductLimited() {
        return productLimited;
    }

    public void setProductLimited(int productLimited) {
        this.productLimited = productLimited;
    }

    public Long getProductSold() {
        return productSold;
    }

    public void setProductSold(Long productSold) {
        this.productSold = productSold;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
