package com.farm.dinh.data.model;

import android.text.TextUtils;

public class FarmerInfo extends Farmer {
    private String street;
    private String ward;
    private String district;
    private String city;
    private int area;

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

    public String getAddress() {
        return getStreet() + (TextUtils.isEmpty(getStreet()) ? "" : ", ") + getWard() + (TextUtils.isEmpty(getWard()) ? "" : ", ") + getDistrict() + (TextUtils.isEmpty(getDistrict()) ? "" : ", ") + getCity();
    }
}
