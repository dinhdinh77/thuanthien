package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class CreateOrderState {
    @Nullable
    private Integer phoneError;
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
    public Integer getQuantityIndexError() {
        return quantityIndexError;
    }

    public void setQuantityIndexError(@Nullable Integer quantityIndexError) {
        this.quantityIndexError = quantityIndexError;
    }

    public boolean isDataVaild() {
        return phoneError == null && quantityIndexError == null;
    }
}
