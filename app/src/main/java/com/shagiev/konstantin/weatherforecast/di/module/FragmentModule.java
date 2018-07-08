package com.shagiev.konstantin.weatherforecast.di.module;

import android.support.annotation.NonNull;

import com.shagiev.konstantin.weatherforecast.di.FragmentScope;
import com.shagiev.konstantin.weatherforecast.network.ForecastNetwork;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;
import com.shagiev.konstantin.weatherforecast.screen.weather.WeatherPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    @Provides
    @FragmentScope
    WeatherPresenter provideWeatherPresenter(@NonNull ForecastRepository forecastRepository, @NonNull ForecastNetwork forecastNetwork) {
        return new WeatherPresenter(forecastNetwork, forecastRepository);
    }
}
