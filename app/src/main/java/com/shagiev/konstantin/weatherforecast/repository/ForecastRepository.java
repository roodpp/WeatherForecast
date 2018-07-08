package com.shagiev.konstantin.weatherforecast.repository;

import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Daily;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;

import java.util.List;

import io.reactivex.Observable;

public interface ForecastRepository {

    void addCity(final ForecastCity forecastCity);

    void deleteCity(String city);

    long checkCitiesCount();

    void addCityList(final List<ForecastCity> forecastCities);

    List<ForecastCity> getCities();

    boolean isCityExist(String cityName);

    void addWeather(Daily daily);

    Observable<List<Day>> getWeather();
}
