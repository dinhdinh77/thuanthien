package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class CreateOrderState {
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer quantityIndexError;

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
    public Integer getQuantityIndexError() {
        return quantityIndexError;
    }

    public void setQuantityIndexError(@Nullable Integer quantityIndexError) {
        this.quantityIndexError = quantityIndexError;
    }

    public boolean isDataVaild() {
        return phoneError == null && nameError == null && quantityIndexError == null;
    }
}
