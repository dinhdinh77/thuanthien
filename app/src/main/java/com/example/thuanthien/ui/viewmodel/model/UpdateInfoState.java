package com.example.thuanthien.ui.viewmodel.model;

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
    private boolean isDataVaild;

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(@Nullable Integer usernameError) {
        this.usernameError = usernameError;
        this.isDataVaild = false;
    }

    @Nullable
    public Integer getOldPasswordError() {
        return oldPasswordError;
    }

    public void setOldPasswordError(@Nullable Integer oldPasswordError) {
        this.oldPasswordError = oldPasswordError;
        this.isDataVaild = false;
    }

    @Nullable
    public Integer getNewPasswordError() {
        return newPasswordError;
    }

    public void setNewPasswordError(@Nullable Integer newPasswordError) {
        this.newPasswordError = newPasswordError;
        this.isDataVaild = false;
    }

    @Nullable
    public Integer getNewPasswordAgainError() {
        return newPasswordAgainError;
    }

    public void setNewPasswordAgainError(@Nullable Integer newPasswordAgainError) {
        this.newPasswordAgainError = newPasswordAgainError;
        this.isDataVaild = false;
    }

    public boolean isDataVaild() {
        return isDataVaild;
    }

    public void setDataVaild(boolean dataVaild) {
        isDataVaild = dataVaild;
    }
}
