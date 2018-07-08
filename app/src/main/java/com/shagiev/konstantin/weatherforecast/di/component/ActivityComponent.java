package com.shagiev.konstantin.weatherforecast.di.component;

import com.shagiev.konstantin.weatherforecast.di.module.ActivityModule;
import com.shagiev.konstantin.weatherforecast.di.ActivityScope;
import com.shagiev.konstantin.weatherforecast.di.module.FragmentModule;
import com.shagiev.konstantin.weatherforecast.screen.cities.CitiesActivity;
import com.shagiev.konstantin.weatherforecast.screen.main.MainActivity;
import com.shagiev.konstantin.weatherforecast.screen.weather.WeatherFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    FragmentComponent plusFragmentComponent(FragmentModule fragmentModule);
    void inject(MainActivity mainActivity);
    void inject(CitiesActivity citiesActivity);
}
