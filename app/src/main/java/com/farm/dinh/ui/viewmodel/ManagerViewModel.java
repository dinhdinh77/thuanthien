package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.repository.LoginRepository;

public class ManagerViewModel extends BaseViewModel<LoginRepository, Boolean> {
    public ManagerViewModel(LoginRepository repository) {
        super(repository);
    }

    public void logout(){
        getRepository().logout();
    }
}
