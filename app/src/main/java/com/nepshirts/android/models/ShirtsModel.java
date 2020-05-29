package com.nepshirts.android.models;

public class ShirtsModel {
    private int shirtImage;
    private String shirtName;
    private String shirtPrice;
    private String shirtCategory;
    private float shirtRating;

    public ShirtsModel(int shirtImage, String shirtName, String shirtPrice, String shirtCategory, float shirtRating) { //, int shirtRating
        this.shirtImage = shirtImage;
        this.shirtName = shirtName;
        this.shirtPrice = shirtPrice;
        this.shirtCategory=shirtCategory;
        this.shirtRating = shirtRating;
    }

    public int getShirtImage() {
        return shirtImage;
    }

    public String getShirtName() {
        return shirtName;
    }

    public String getShirtPrice() {
        return shirtPrice;
    }

    public String getShirtCategory() {
        return shirtCategory;
    }

    public float getShirtRating() {
        return shirtRating;
    }
}
