package com.example.tertan.Model;

public class Stadium_model_original {
    String item_id;
    String userid;
    String stadium_name;
    String stadium_address;
    String properties;
    String longitude;
    String latitude;
    String price;
    String imageURL;

    public Stadium_model_original(String item_id, String userid, String stadium_name, String stadium_address, String properties, String longitude, String latitude, String price, String imageURL) {
        this.item_id = item_id;
        this.userid = userid;
        this.stadium_name = stadium_name;
        this.stadium_address = stadium_address;
        this.properties = properties;
        this.longitude = longitude;
        this.latitude = latitude;
        this.price = price;
        this.imageURL = imageURL;
    }
    public Stadium_model_original(){

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public void setStadium_name(String stadium_name) {
        this.stadium_name = stadium_name;
    }

    public String getStadium_address() {
        return stadium_address;
    }

    public void setStadium_address(String stadium_address) {
        this.stadium_address = stadium_address;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
