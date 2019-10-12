package com.farm.dinh.data.model;

import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserInfo {
    private int userId;
    private String name;
    private String phone;
    private String birthday;
    private String street;
    private String ward;
    private String district;
    private String city;
    private int area;
    private int isAgency;
    private List<FarmerInfo> farmers;

    public int getId() {
        return userId;
    }

    public void setId(int userId) {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(int isAgency) {
        this.isAgency = isAgency;
    }

    public List<FarmerInfo> getFarmers() {
        return farmers;
    }

    public void setFarmers(List<FarmerInfo> farmers) {
        this.farmers = farmers;
    }
}
