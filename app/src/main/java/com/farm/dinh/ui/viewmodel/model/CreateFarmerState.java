package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class CreateFarmerState {
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer streetError;
    @Nullable
    private Integer cityError;
    @Nullable
    private Integer districtError;
    @Nullable
    private Integer wardError;

    @Nullable
    public Integer getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(@Nullable Integer phoneError) {
        this.phoneError = phoneError;
    }

    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    public void setNameError(@Nullable Integer nameError) {
        this.nameError = nameError;
    }

    @Nullable
    public Integer getStreetError() {
        return streetError;
    }

    public void setStreetError(@Nullable Integer streetError) {
        this.streetError = streetError;
    }

    @Nullable
    public Integer getCityError() {
        return cityError;
    }

    public void setCityError(@Nullable Integer cityError) {
        this.cityError = cityError;
    }

    @Nullable
    public Integer getDistrictError() {
        return districtError;
    }

    public void setDistrictError(@Nullable Integer districtError) {
        this.districtError = districtError;
    }

    @Nullable
    public Integer getWardError() {
        return wardError;
    }

    public void setWardError(@Nullable Integer wardError) {
        this.wardError = wardError;
    }

    public boolean isDataVaild(){
        return phoneError == null && nameError == null && cityError == null
                && districtError == null && wardError == null && streetError == null;
    }
}
