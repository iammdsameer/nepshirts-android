package com.nepshirts.android.models;

public class OrderModel {
    String productId, userId, color, size, quantity;

    public OrderModel() {
    }

    public OrderModel(String productId, String userId, String color, String size, String quantity) {
        this.productId = productId;
        this.userId = userId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
