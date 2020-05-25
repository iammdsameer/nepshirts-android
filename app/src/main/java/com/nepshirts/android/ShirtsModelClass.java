package com.nepshirts.android;

public class ShirtsModelClass {
    private int shirtImage;
    private String shirtName;
    private String shirtPrice;
    private String shirtCategory;
//    private int shirtRating;

    public ShirtsModelClass(int shirtImage, String shirtName, String shirtPrice, String shirtCategory) { //, int shirtRating
        this.shirtImage = shirtImage;
        this.shirtName = shirtName;
        this.shirtPrice = shirtPrice;
        this.shirtCategory=shirtCategory;
//        this.shirtRating = shirtRating;
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

//    public int getShirtRating() {
//        return shirtRating;
//    }
}
