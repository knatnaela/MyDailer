package com.example.toshiba.myapplication.Model;


public class ModelContacts  {

    public String name,number,Id;
    private String  Photo;

    public ModelContacts(String name, String number, String id, String photo) {
        this.name = name;
        this.number = number;
        Id = id;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
   }
}
