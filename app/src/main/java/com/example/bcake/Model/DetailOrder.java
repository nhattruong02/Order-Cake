package com.example.bcake.Model;

public class DetailOrder {
    private int Id;
    private int Id_Order;
    private int Id_Product;
    private int Quantity;
    private double Price;
    private String nameOfProduct;
    private String Image;
    private String Message;

    public DetailOrder(int id, int id_Order, int id_Product, int quantity, double price, String nameOfProduct, String image, String message) {
        Id = id;
        Id_Order = id_Order;
        Id_Product = id_Product;
        Quantity = quantity;
        Price = price;
        this.nameOfProduct = nameOfProduct;
        Image = image;
        Message = message;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_Order() {
        return Id_Order;
    }

    public void setId_Order(int id_Order) {
        Id_Order = id_Order;
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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
