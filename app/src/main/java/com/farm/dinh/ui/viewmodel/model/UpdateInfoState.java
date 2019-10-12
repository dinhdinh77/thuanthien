package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class UpdateInfoState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer oldPasswordError;
    @Nullable
    private Integer newPasswordError;
    @Nullable
    private Integer newPasswordAgainError;
    @Nullable
    private Integer streetError;
    @Nullable
    private Integer cityError;
    @Nullable
    private Integer districtError;
    @Nullable
    private Integer wardError;
    private boolean isChangePass;

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(@Nullable Integer usernameError) {
        this.usernameError = usernameError;
    }

    @Nullable
    public Integer getOldPasswordError() {
        return oldPasswordError;
    }

    public void setOldPasswordError(@Nullable Integer oldPasswordError) {
        this.oldPasswordError = oldPasswordError;
    }

    @Nullable
    public Integer getNewPasswordError() {
        return newPasswordError;
    }

    public void setNewPasswordError(@Nullable Integer newPasswordError) {
        this.newPasswordError = newPasswordError;
    }

    @Nullable
    public Integer getNewPasswordAgainError() {
        return newPasswordAgainError;
    }

    public void setNewPasswordAgainError(@Nullable Integer newPasswordAgainError) {
        this.newPasswordAgainError = newPasswordAgainError;
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

    public void setChangePass(boolean changePass) {
        isChangePass = changePass;
    }

    public boolean isDataVaild() {
        return usernameError == null && isChangePass ? oldPasswordError == null && newPasswordError == null && newPasswordAgainError == null : true;
    }
}
