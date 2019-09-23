package com.farm.dinh.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.farm.dinh.ui.viewmodel.ConfigViewModel;
import com.farm.dinh.ui.viewmodel.ViewModelFactory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigViewModel configViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ConfigViewModel.class);
        configViewModel.getAddress();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
