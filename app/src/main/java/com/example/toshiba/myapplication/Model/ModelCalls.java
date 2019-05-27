package com.example.toshiba.myapplication.Model;


import android.widget.ImageView;

public class ModelCalls  {

    public String name,number,date,photo;

    private String Id;

    private String type;

    public ModelCalls() {
    }


    public ModelCalls(String name, String number, String date, String photo, String id, String type) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.photo = photo;
        Id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String  getId() {
        return Id;
    }

    public void setId(String  id) {
        Id = id;
    }
}
