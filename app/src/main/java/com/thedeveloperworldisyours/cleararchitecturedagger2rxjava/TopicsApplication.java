package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava;

import android.app.Application;

/**
 * Created by javierg on 03/07/2017.
 */

public class TopicsApplication extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
