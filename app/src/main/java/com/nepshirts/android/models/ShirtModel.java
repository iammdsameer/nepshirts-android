package com.nepshirts.android.models;

public class ShirtModel {
    private String id;
    private String productNames;
    private String imageUrl;
    private String price;
    private String rating;
    private String description;
    private String disPrice;
    private boolean productSizeXL;
    private boolean getProductSizeXLL;

    private String productCategory;

    public ShirtModel() {
    }

    public ShirtModel(String id, String productNames, String imageUrl, String price, String rating, String description, String disPrice, Boolean productSizeXL, Boolean getProductSizeXLL, String productCategory) {
        this.id = id;
        this.productNames = productNames;
        this.imageUrl = imageUrl;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.disPrice = disPrice;
        this.productSizeXL = productSizeXL;
        this.getProductSizeXLL = getProductSizeXLL;
        this.productCategory = productCategory;
    }

    public String getId() {
        return id;
    }

    public String getProductNames() {
        return productNames;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() { return rating; }

    public String getDescription() {
        return description;
    }

    public String getDisPrice() {
        return disPrice;
    }

    public Boolean getProductSizeXL() {
        return productSizeXL;
    }

    public boolean isGetProductSizeXLL() {
        return getProductSizeXLL;
    }
    public String getProductCategory() { return productCategory;}
}
