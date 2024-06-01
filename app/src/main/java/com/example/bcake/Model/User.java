package com.example.bcake.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @SerializedName("Id_User")
    private int Id;
    private String Name;
    private boolean Gender;
    private String Address;
    private Date yearOfBirth;
    private String phoneNumber;
    private String userName;
    private String passWord;
    private String Avatar;
    private String Role;

    public User(int id, String name, boolean gender, String address, Date yearOfBirth, String phoneNumber, String userName, String passWord, String avatar, String role) {
        Id = id;
        Name = name;
        Gender = gender;
        Address = address;
        this.yearOfBirth = yearOfBirth;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.passWord = passWord;
        Avatar = avatar;
        Role = role;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Date getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Date yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
    
}
