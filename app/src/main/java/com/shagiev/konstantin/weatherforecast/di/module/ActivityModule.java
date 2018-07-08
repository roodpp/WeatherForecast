package com.shagiev.konstantin.weatherforecast.di.module;


import android.support.annotation.NonNull;

import com.shagiev.konstantin.weatherforecast.di.ActivityScope;
import com.shagiev.konstantin.weatherforecast.network.ForecastNetwork;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;
import com.shagiev.konstantin.weatherforecast.screen.cities.CitiesPresenter;
import com.shagiev.konstantin.weatherforecast.screen.main.MainPresenter;
import com.shagiev.konstantin.weatherforecast.screen.weather.WeatherPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(@NonNull ForecastRepository forecastRepository, @NonNull ForecastNetwork forecastNetwork) {
        return new MainPresenter(forecastNetwork, forecastRepository);
    }

    @Provides
    @ActivityScope
    CitiesPresenter provideCitiesPresenter(@NonNull ForecastRepository forecastRepository, @NonNull ForecastNetwork forecastNetwork) {
        return new CitiesPresenter(forecastNetwork, forecastRepository);
    }
}
