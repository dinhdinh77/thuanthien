package com.example.thuanthien;

import android.app.Application;

public class TTApplication extends Application {
    private static TTApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static TTApplication getInstance() {
        return TTApplication.sInstance;
    }
}
