package com.example.bcake.Model;

import java.util.Date;

public class Review {
    private int Id;
    private int Id_User;
    private int Id_Product;
    private String Image;
    private String nameUser;
    private float numberOfStar;
    private String Comment;
    private Date Time;

    public Review(int id, int id_User, int id_Product, String image, String nameUser, float numberOfStar, String comment, Date time) {
        Id = id;
        Id_User = id_User;
        Id_Product = id_Product;
        Image = image;
        this.nameUser = nameUser;
        this.numberOfStar = numberOfStar;
        Comment = comment;
        Time = time;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public float getNumberOfStar() {
        return numberOfStar;
    }

    public void setNumberOfStar(float numberOfStar) {
        this.numberOfStar = numberOfStar;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }
}
