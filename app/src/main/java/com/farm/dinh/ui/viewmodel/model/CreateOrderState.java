package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class CreateOrderState {
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer productError;
    @Nullable
    private Integer quantityError;
    private String name;

    @Nullable
    public Integer getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(@Nullable Integer phoneError) {
        this.phoneError = phoneError;
    }

    @Nullable
    public Integer getProductError() {
        return productError;
    }

    public void setProductError(@Nullable Integer productError) {
        this.productError = productError;
    }

    @Nullable
    public Integer getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(@Nullable Integer quantityError) {
        this.quantityError = quantityError;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDataVaild() {
        return phoneError == null && productError == null && quantityError == null;
    }
}
