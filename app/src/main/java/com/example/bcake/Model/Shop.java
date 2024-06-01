package com.example.bcake.Model;

public class Shop {
    private int Id;
    private int Id_User;
    private String Name;
    private String Description;
    private String Address;
    private String phoneNumber;
    private String Avatar;
    private float Longitude;
    private float Latitude;
    private boolean Status;

    public Shop(int id, int id_User, String name, String description, String address, String phoneNumber, String avatar, float longtitude, float latitude, boolean status) {
        Id = id;
        Id_User = id_User;
        Name = name;
        Description = description;
        Address = address;
        this.phoneNumber = phoneNumber;
        Avatar = avatar;
        Longitude = longtitude;
        Latitude = latitude;
        Status = status;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongtitude(float longtitude) {
        Longitude = longtitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "Id=" + Id +
                ", Id_User=" + Id_User +
                ", Avatar='" + Avatar + '\'' +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                '}';
    }
}
