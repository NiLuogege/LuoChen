package com.example.well.luochen.net.rxretrofit;

import android.app.Application;

/**
 * 全局app
 */

public class RxRetrofitApp {
    private static Application application;

    public static void init(Application app){
        setApplication(app);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }
}
