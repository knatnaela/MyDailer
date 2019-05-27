package com.example.toshiba.myapplication.Model;

public class ModelFav {

    public String name,number;
    private String  Photo;

    public ModelFav(String name, String number, String photo) {
        this.name = name;
        this.number = number;
        Photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
