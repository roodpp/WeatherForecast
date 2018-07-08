package com.shagiev.konstantin.weatherforecast;


import android.app.Application;

import com.shagiev.konstantin.weatherforecast.di.component.AppComponent;
import com.shagiev.konstantin.weatherforecast.di.module.AppModule;
import com.shagiev.konstantin.weatherforecast.di.component.DaggerAppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ForecastApplication extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration
                        .Builder()
                        .compactOnLaunch()
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();

    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

}
