package com.shagiev.konstantin.weatherforecast.screen.main;

import com.shagiev.konstantin.weatherforecast.network.ForecastNetwork;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;

import java.util.Collections;
import java.util.List;

public class MainPresenter {

    private ForecastNetwork mForecastNetwork;
    private ForecastRepository mForecastRepository;
    private MainView mMainView;

    public MainPresenter(ForecastNetwork forecastNetwork, ForecastRepository forecastRepository) {
        mForecastNetwork = forecastNetwork;
        mForecastRepository = forecastRepository;
    }

    public void onAttach(MainView mainView){
        mMainView =  mainView;
    }

    public void onCitiesListEmpty(){

    }

    public void loadCitiesList(){
        List<ForecastCity> cities = mForecastRepository.getCities();
        Collections.reverse(cities);
        mMainView.initCitiesList(cities);
    }
}
