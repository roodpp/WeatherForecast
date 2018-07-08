package com.shagiev.konstantin.weatherforecast.di.component;

import com.shagiev.konstantin.weatherforecast.di.FragmentScope;
import com.shagiev.konstantin.weatherforecast.di.module.FragmentModule;
import com.shagiev.konstantin.weatherforecast.screen.weather.WeatherFragment;


import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(WeatherFragment weatherFragment);
}
