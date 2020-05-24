package com.sergey.workapp;

import android.app.Application;

import com.sergey.workapp.dao.DaoManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.init(this);
    }

    @Override
    public void onTerminate() {
        DaoManager.terminate();
        super.onTerminate();
    }
}
