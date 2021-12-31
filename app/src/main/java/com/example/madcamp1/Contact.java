package com.example.madcamp1;

public class Contact {
    private String name;
    private String phoneNumber;
    private int photo;

    public Contact() {
        this.photo = R.mipmap.ic_launcher_round;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phoneNumber = phone;
        this.photo = R.mipmap.ic_launcher_round;
    }

    public Contact(String name, String phone, int photo) {
        this.name = name;
        this.phoneNumber = phone;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public int getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoto() {
        this.photo = photo;
    }
}
