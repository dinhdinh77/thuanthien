package com.farm.dinh.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.farm.dinh.ui.viewmodel.BaseViewModel;

public abstract class BaseFragment<D extends BaseViewModel> extends Fragment {
    public abstract Class<D> getViewModelType();

    public final D getViewModel() {
        return ViewModelProviders.of(this).get(getViewModelType());
    }

    public final D getViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(getViewModelType());
    }
}
