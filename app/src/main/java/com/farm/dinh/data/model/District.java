package com.farm.dinh.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class District {
    @SerializedName("district_code")
    private String districtCode;
    private String name;
    private String type;
    private List<Ward> ward;

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
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

    public List<Ward> getWard() {
        return ward;
    }

    public void setWard(List<Ward> ward) {
        this.ward = ward;
    }
}
