package com.example.ximena.tc6_ximenabolannos;

/**
 * Created by Ximena on 24/5/2018.
 */

public final class Product {
    public String name;
    public String price;
    public String description;
    public String image;
    public String id;
    public String userid;

    public Product(String name, String price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Product() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
