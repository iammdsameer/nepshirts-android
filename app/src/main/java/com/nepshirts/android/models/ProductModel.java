package com.nepshirts.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {
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

    public ProductModel() {
    }

    public ProductModel(String id, String productNames, String imageUrl, String price, String rating, String description, String disPrice, Boolean productSizeXL, Boolean getProductSizeXLL, String productCategory) {
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

    protected ProductModel(Parcel in) {
        id = in.readString();
        productNames = in.readString();
        imageUrl = in.readString();
        price = in.readString();
        rating = in.readString();
        description = in.readString();
        disPrice = in.readString();
        productSizeXL = in.readByte() != 0;
        getProductSizeXLL = in.readByte() != 0;
        productCategory = in.readString();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productNames);
        dest.writeString(imageUrl);
        dest.writeString(price);
        dest.writeString(rating);
        dest.writeString(description);
        dest.writeString(disPrice);
        dest.writeByte((byte) (productSizeXL ? 1 : 0));
        dest.writeByte((byte) (getProductSizeXLL ? 1 : 0));
        dest.writeString(productCategory);
    }
}
