package com.example.bcake.Model;

import java.util.Date;

public class Product {
    private int Id_Product;
    private String Name;
    private double Price;
    private String Description;
    private String CreatedDate;
    private String Size;
    private String Shape;
    private String Flavor;
    private String Weight;
    private String Image;
    private int Id_Shop;

    public Product(int id_Product, String name, double price, String description, String createdDate, String size, String shape, String flavor, String weight, String image, int id_Shop) {
        Id_Product = id_Product;
        Name = name;
        Price = price;
        Description = description;
        CreatedDate = createdDate;
        Size = size;
        Shape = shape;
        Flavor = flavor;
        Weight = weight;
        Image = image;
        Id_Shop = id_Shop;
    }

    public Product(String name) {
        Name = name;
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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getShape() {
        return Shape;
    }

    public void setShape(String shape) {
        Shape = shape;
    }

    public String getFlavor() {
        return Flavor;
    }

    public void setFlavor(String flavor) {
        Flavor = flavor;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getId_Shop() {
        return Id_Shop;
    }

    public void setId_Shop(int id_Shop) {
        Id_Shop = id_Shop;
    }
}
