package com.nepshirts.android.models;

public class UserModel {

    String fullName, userEmail, userPhoneNumber, userGender, userBirthDate;
    String city, street, landmark;


    public UserModel() {
    }

    public UserModel(String fullName, String userEmail, String userPhoneNumber, String userGender, String userBirthDate, String city, String street, String landmark) {
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userGender = userGender;
        this.userBirthDate = userBirthDate;
        this.city = city;
        this.street = street;
        this.landmark = landmark;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getLandmark() {
        return landmark;
    }
}