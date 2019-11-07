package com.farm.dinh.ui.viewmodel;

import com.farm.dinh.repository.LoginRepository;

public class ManagerViewModel extends BaseViewModel<Boolean> {
    public void logout() {
        getRepository(LoginRepository.class).logout();
    }
}
