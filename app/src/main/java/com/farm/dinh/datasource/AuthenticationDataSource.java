package com.farm.dinh.datasource;


import com.farm.dinh.data.model.UserInfo;
import com.farm.dinh.remote.IAuthenticationService;
import com.farm.dinh.repository.IRepository;


public class AuthenticationDataSource extends DataSource<IAuthenticationService> {

    public void login(String phone, String password, final IRepository<UserInfo> listener) {
        getRemoteService().login(phone, password).enqueue(getStandardCallBack(listener));
    }

    public void logout() {
        // TODO: revoke authentication
    }

    @Override
    public Class<IAuthenticationService> getServiceType() {
        return IAuthenticationService.class;
    }
}
