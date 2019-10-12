package com.farm.dinh.ui.viewmodel.model;

import androidx.annotation.Nullable;

public class ViewResult<D> {
    @Nullable
    private D success;
    @Nullable
    private String errorMsg;
    private boolean isFlag;

    public ViewResult(@Nullable D success, boolean isFlag) {
        this.success = success;
        this.isFlag = isFlag;
    }

    public ViewResult(@Nullable String errorMsg, boolean isFlag) {
        this.errorMsg = errorMsg;
        this.isFlag = isFlag;
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
        return isFlag;
    }

    public boolean isLoadMore() {
        return isFlag;
    }

    public boolean isSuccess(){
        return isFlag;
    }
}
