package com.farm.dinh.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {
    @SerializedName("city_code")
    private String cityCode;
    private String name;
    private String type;
    private List<District> district;

    public City(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj != null && obj instanceof City && ((City) obj).getName().equals(this.getName()));
    }
}
