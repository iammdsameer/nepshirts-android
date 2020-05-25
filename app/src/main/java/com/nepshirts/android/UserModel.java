package com.nepshirts.android;

public class UserModel {

    String fullName,userEmail,userPhoneNumber,userGender,userBirthDate;


    public UserModel() {
    }

    public UserModel(String fullName, String userEmail, String userPhoneNumber, String userGender, String userBirthDate) {

        this.fullName = fullName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userGender = userGender;
        this.userBirthDate = userBirthDate;
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
}
