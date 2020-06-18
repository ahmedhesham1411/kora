package com.example.tertan.Model;

public class Stadium_model {
    int image;
    String name;
    String place;
    int price;

    public Stadium_model(int image, String name, String place,int price) {
        this.image = image;
        this.name = name;
        this.place = place;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
