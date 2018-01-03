package com.example.mubarak.foodmenu.Model;

/**
 * Created by mubarak on 12/11/17.
 */

public class Category {

    private String Name;
    private String Image;

    public Category() {

    }

    public Category(String image, String name) {
        Name = name;
        Image = image;
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
}
