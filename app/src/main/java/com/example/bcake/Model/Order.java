package com.example.bcake.Model;

import java.util.Date;

public class Order {
    private int Id;
    private int Id_User;
    private int Id_Shop;
    private String Image;
    private String nameOfShop;
    private String Address;
    private String phoneNumber;
    private String nameOfCustomer;
    private String addressOfCustomer;
    private double totalOfBill;
    // Receive Order
    private String Status;
    private boolean paymentStatus;
    private String paymentMethod;
    private String oderTime;
    private String Token;

    public Order(int id, int id_User, int id_Shop, String image, String nameOfShop, String address, String phoneNumber, String nameOfCustomer, String addressOfCustomer, double totalOfBill, String status, boolean paymentStatus, String paymentMethod, String oderTime, String token) {
        Id = id;
        Id_User = id_User;
        Id_Shop = id_Shop;
        Image = image;
        this.nameOfShop = nameOfShop;
        Address = address;
        this.phoneNumber = phoneNumber;
        this.nameOfCustomer = nameOfCustomer;
        this.addressOfCustomer = addressOfCustomer;
        this.totalOfBill = totalOfBill;
        Status = status;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.oderTime = oderTime;
        Token = token;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public int getId_Shop() {
        return Id_Shop;
    }

    public void setId_Shop(int id_Shop) {
        Id_Shop = id_Shop;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNameOfShop() {
        return nameOfShop;
    }

    public void setNameOfShop(String nameOfShop) {
        this.nameOfShop = nameOfShop;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public String getAddressOfCustomer() {
        return addressOfCustomer;
    }

    public void setAddressOfCustomer(String addressOfCustomer) {
        this.addressOfCustomer = addressOfCustomer;
    }

    public double getTotalOfBill() {
        return totalOfBill;
    }

    public void setTotalOfBill(double totalOfBill) {
        this.totalOfBill = totalOfBill;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOderTime() {
        return oderTime;
    }

    public void setOderTime(String oderTime) {
        this.oderTime = oderTime;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

}
