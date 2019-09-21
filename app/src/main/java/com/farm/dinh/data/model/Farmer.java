package com.farm.dinh.data.model;

public class Farmer {
    private int userId;
    private String name;
    private String phone;

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
