package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.model.City;
import com.farm.dinh.repository.LoginRepository;
import com.farm.dinh.repository.MainRepository;

import java.util.List;

public class ConfigViewModel extends BaseViewModel<LoginRepository, List<City>> {
    public ConfigViewModel(LoginRepository repository) {
        super(repository);
    }

    public void getAddress(){
        getRepository().getAddress(null);
    }
}
