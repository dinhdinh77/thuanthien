package com.example.thuanthien.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.thuanthien.datasource.LoginDataSource;
import com.example.thuanthien.datasource.MainDataSource;
import com.example.thuanthien.repository.LoginRepository;
import com.example.thuanthien.repository.MainRepository;
import com.example.thuanthien.ui.viewmodel.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(MainRepository.getInstance(new MainDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
