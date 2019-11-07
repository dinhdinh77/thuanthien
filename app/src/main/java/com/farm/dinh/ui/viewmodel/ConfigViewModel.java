package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.model.City;
import com.farm.dinh.repository.LoginRepository;

import java.util.List;

public class ConfigViewModel extends BaseViewModel<List<City>> {
    public void getAddress() {
        getRepository(LoginRepository.class).getAddress(null);
    }
}
