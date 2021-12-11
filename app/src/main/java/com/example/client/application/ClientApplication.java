package com.example.client.application;

import android.app.Application;

public class ClientApplication extends Application {
    private static ClientApplication sApplication;

    public static ClientApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

}
