package com.shagiev.konstantin.weatherforecast.screen.main;

import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;

import java.util.List;

public interface MainView {

    void initCitiesList(List<ForecastCity> cities);
}
