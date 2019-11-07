package com.farm.dinh.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.farm.dinh.ui.viewmodel.BaseViewModel;

public abstract class BaseActivity<D extends BaseViewModel> extends AppCompatActivity {

    public abstract Class<D> getViewModelType();

    public final D getViewModel() {
        return ViewModelProviders.of(this).get(getViewModelType());
    }
}
