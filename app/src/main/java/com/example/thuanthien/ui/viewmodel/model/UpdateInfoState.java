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
}
