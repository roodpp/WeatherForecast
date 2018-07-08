package com.shagiev.konstantin.weatherforecast.screen.weather;

import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;
import com.shagiev.konstantin.weatherforecast.screen.LoadingView;

import java.util.List;

public interface WeatherView extends LoadingView{
    void openCitiesActivity();
    void showWeather(List<Day> weather);
}
