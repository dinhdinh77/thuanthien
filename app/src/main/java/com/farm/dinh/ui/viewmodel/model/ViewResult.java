package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class ViewResult<D> {
    @Nullable
    private D success;
    @Nullable
    private String errorMsg;
    private boolean isUpdate;

    public ViewResult(@Nullable D success, boolean isUpdate) {
        this.success = success;
        this.isUpdate = isUpdate;
    }

    public ViewResult(@Nullable String errorMsg, boolean isUpdate) {
        this.errorMsg = errorMsg;
        this.isUpdate = isUpdate;
    }

    public ViewResult(@Nullable D success) {
        this.success = success;
    }

    public ViewResult(@Nullable String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Nullable
    public String getError() {
        return errorMsg;
    }

    @Nullable
    public D getSuccess() {
        return success;
    }

    public boolean isUpdate() {
        return isUpdate;
    }
}
