package com.shagiev.konstantin.weatherforecast.network;


import com.shagiev.konstantin.weatherforecast.network.model.response.CurrentWeather;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;


import java.util.List;

import io.reactivex.Observable;


public interface ForecastNetwork {
    Observable<CurrentWeather> currentWeather(String cities);
    Observable<List<Day>> weather(String cityName);
}
