package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class CreateTreeState {
    @Nullable
    private Integer treeError;
    @Nullable
    private Integer ageError;
    @Nullable
    private Integer quantityError;

    @Nullable
    public Integer getTreeError() {
        return treeError;
    }

    public void setTreeError(@Nullable Integer treeError) {
        this.treeError = treeError;
    }

    @Nullable
    public Integer getAgeError() {
        return ageError;
    }

    public void setAgeError(@Nullable Integer ageError) {
        this.ageError = ageError;
    }

    @Nullable
    public Integer getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(@Nullable Integer quantityError) {
        this.quantityError = quantityError;
    }

    public boolean isDataVaild() {
        return treeError == null && ageError == null && quantityError == null;
    }
}
