package com.example.user;

public class userModelClass {

   int id;
    private String fname, lname, number, email, gender, hobby, date, data;
    private byte[] image;

    public userModelClass(String fname, String lname, String email, String number, String date, String gender, String hobby, String data, byte[] image) {

        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.number = number;
        this.date = date;
        this.gender = gender;
        this.hobby = hobby;
        this.data = data;
        this.image  = image;
    }

    public userModelClass(int id, String fname, String lname, String email, String number, String date, String gender, String hobby, String data,byte[] image) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.number = number;
        this.date = date;
        this.gender = gender;
        this.hobby = hobby;
        this.data = data;
        this.image=image;
    }



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


