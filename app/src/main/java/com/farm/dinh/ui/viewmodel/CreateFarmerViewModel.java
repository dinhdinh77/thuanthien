package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.data.model.Farmer;
import com.farm.dinh.repository.MainRepository;

public class CreateFarmerViewModel extends BaseViewModel<MainRepository, Farmer> {
    public CreateFarmerViewModel(MainRepository repository) {
        super(repository);
    }
}
