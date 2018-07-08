package com.shagiev.konstantin.weatherforecast.di.component;

import com.shagiev.konstantin.weatherforecast.di.module.ActivityModule;
import com.shagiev.konstantin.weatherforecast.di.module.AppModule;
import com.shagiev.konstantin.weatherforecast.screen.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent{

    ActivityComponent plusActivityComponent(ActivityModule activityModule);

    void injectSplashActivity(SplashActivity activity);
}
