package com.example.bcake.Model;

import java.util.List;

public class Cart {
    private int Id;
    private int Id_User;
    private int Id_Product;
    private int Quantity;
    private String Image;
    private String Name;
    private double Price;
    private String Message;

    public Cart(int id, int id_User, int id_Product, int quantity, String image, String name, double price, String message) {
        Id = id;
        Id_User = id_User;
        Id_Product = id_Product;
        Quantity = quantity;
        Image = image;
        Name = name;
        Price = price;
        Message = message;
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

    public int getId_Product() {
        return Id_Product;
    }

    public void setId_Product(int id_Product) {
        Id_Product = id_Product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
