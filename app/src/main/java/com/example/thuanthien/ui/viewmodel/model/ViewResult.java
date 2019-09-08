package com.example.thuanthien.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class ViewResult<D> {
    @Nullable
    private D success;
    @Nullable
    private String errorMsg;

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
}
