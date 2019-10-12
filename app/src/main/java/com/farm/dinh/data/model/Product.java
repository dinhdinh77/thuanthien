package com.farm.dinh.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName(value = "productId", alternate = {"id"})
    private int productId;
    private String name;
    private int quantity;

    public int getId() {
        return productId;
    }

    public void setId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj != null && obj instanceof Product && ((Product) obj).getId() == this.getId());
    }
}
