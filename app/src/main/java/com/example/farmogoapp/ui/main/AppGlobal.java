package com.example.farmogoapp.ui.main;



import android.app.Application;

public class AppGlobal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.init(this);
        SessionData.init(this);
    }



    @Override
    public void onTerminate() {
        CacheManager.getInstance().shutdown();
        super.onTerminate();
    }
}
