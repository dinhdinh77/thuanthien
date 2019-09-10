package com.example.thuanthien.repository;

import com.example.thuanthien.data.model.Questions;
import com.example.thuanthien.data.model.UserInfo;
import com.example.thuanthien.datasource.MainDataSource;
import com.example.thuanthien.local.Pref;

public class MainRepository extends Repository<MainDataSource> {
    private static volatile MainRepository instance;

    public MainRepository(MainDataSource dataSource) {
        super(dataSource);
    }

    public static MainRepository getInstance(MainDataSource dataSource) {
        if (instance == null) {
            instance = new MainRepository(dataSource);
        }
        return instance;
    }

    public void getQuestionsList(IRepository<Questions> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getQuestionsList(currUserId, listener);
    }

    public void getUserInfo(IRepository<UserInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().getUserInfo(currUserId, listener);
    }

    public void updateUserInfo(String name, String old_password, String new_password, IRepository<UserInfo> listener) {
        int currUserId = Pref.getInstance().get(Pref.KEY_USER_ID, 0);
        getDataSource().updateUserInfo(currUserId, name, old_password, new_password, listener);
    }
}
