package com.farm.dinh.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.farm.dinh.datasource.LoginDataSource;
import com.farm.dinh.datasource.MainDataSource;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.repository.MainRepository;

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
        } else if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else if (modelClass.isAssignableFrom(OrderManagerViewModel.class)) {
            return (T) new OrderManagerViewModel(MainRepository.getInstance(new MainDataSource()));
        } else if (modelClass.isAssignableFrom(CreateOrderViewModel.class)) {
            return (T) new CreateOrderViewModel(MainRepository.getInstance(new MainDataSource()));
        } else if (modelClass.isAssignableFrom(ConfigViewModel.class)) {
            return (T) new ConfigViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else if (modelClass.isAssignableFrom(CreateFarmerViewModel.class)) {
            return (T) new CreateFarmerViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else if (modelClass.isAssignableFrom(ManagerViewModel.class)) {
            return (T) new ManagerViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else if (modelClass.isAssignableFrom(FarmerManagerViewModel.class)) {
            return (T) new FarmerManagerViewModel(MainRepository.getInstance(new MainDataSource()));
        } else if (modelClass.isAssignableFrom(TreeManagerViewModel.class)) {
            return (T) new TreeManagerViewModel(MainRepository.getInstance(new MainDataSource()));
        } else if (modelClass.isAssignableFrom(CreateTreeViewModel.class)) {
            return (T) new CreateTreeViewModel(MainRepository.getInstance(new MainDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
