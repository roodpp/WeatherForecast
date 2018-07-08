package com.shagiev.konstantin.weatherforecast.screen.cities;

import com.shagiev.konstantin.weatherforecast.network.model.response.CurrentWeather;
import com.shagiev.konstantin.weatherforecast.screen.LoadingView;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;

import java.util.List;

public interface CitiesView extends LoadingView {

    void initExistCities(List<ForecastCity> cities);

    void showSearchedCity(CurrentWeather currentWeather);

    void onCitiesListChange();

    void onCityNotDeleted();

    void onCityNotAdded();

}
