package com.farm.dinh.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.farm.dinh.ui.viewmodel.ConfigViewModel;

public class SplashScreenActivity extends BaseActivity<ConfigViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().getAddress();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Class<ConfigViewModel> getViewModelType() {
        return ConfigViewModel.class;
    }
}
