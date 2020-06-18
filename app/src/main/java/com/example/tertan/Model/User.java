package com.example.tertan.Model;

public class User {
    private String id;
    private String username;
    private String Password;
    private String email;

    private String imageURL;
    private String status;
    private String phone;

    public User(String id, String username, String password, String email, String imageURL, String status, String phone) {
        this.id = id;
        this.username = username;
        Password = password;
        this.email = email;
        this.imageURL = imageURL;
        this.status = status;
        this.phone = phone;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
