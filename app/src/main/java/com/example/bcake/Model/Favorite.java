package com.example.bcake.Model;

public class Favorite {
    private int Id;
    private int Id_User;
    private int Id_Product;
    private String Name;
    private String Image;
    private double Price;
    private int Id_Shop;

    public Favorite(int id, int id_User, int id_Product, String name, String image, double price, int id_Shop) {
        Id = id;
        Id_User = id_User;
        Id_Product = id_Product;
        Name = name;
        Image = image;
        Price = price;
        Id_Shop = id_Shop;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getId_Shop() {
        return Id_Shop;
    }

    public void setId_Shop(int id_Shop) {
        Id_Shop = id_Shop;
    }
}
