package com.shagiev.konstantin.weatherforecast.screen.cities;

import com.shagiev.konstantin.weatherforecast.network.ForecastNetwork;
import com.shagiev.konstantin.weatherforecast.network.model.response.CurrentWeather;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CitiesPresenter {

    private ForecastNetwork mNetwork;
    private ForecastRepository mRepository;
    private CitiesView mView;
    private CompositeDisposable mCompositeDisposable;

    public CitiesPresenter(ForecastNetwork forecastNetwork, ForecastRepository forecastRepository) {
        mNetwork = forecastNetwork;
        mRepository = forecastRepository;
        mCompositeDisposable = new CompositeDisposable();
    }


    public void onAttach(CitiesView citiesView){
        mView =  citiesView;
    }

    public void onDetach(){
        mCompositeDisposable.clear();
        mView = null;
    }

    public void loadExistCities(){
        List<ForecastCity> cities = mRepository.getCities();
        Collections.reverse(cities);
        mView.initExistCities(cities);
    }

    public void onSearchClick(String cityName){
        mCompositeDisposable.add(mNetwork.currentWeather(cityName)
                .doOnSubscribe(disposable -> mView.showLoading())
                .doOnTerminate(mView::hideLoading)
                .subscribe(mView::showSearchedCity, throwable -> mView.showError(throwable.getMessage())));
    }

    public void  onAddCityClick(CurrentWeather currentWeather){
        boolean cityExist = mRepository.isCityExist(currentWeather.getName());
        if(cityExist){
            mView.onCityNotAdded();
        } else{
            mRepository.addCity(new ForecastCity(currentWeather.getId(), currentWeather.getName()));
            loadExistCities();
            mView.onCitiesListChange();
        }
    }

    public void onDeleteCityClick(String cityName){
        long citiesCount = mRepository.checkCitiesCount();
        if(citiesCount > 1) {
            mRepository.deleteCity(cityName);
            loadExistCities();
            mView.onCitiesListChange();
        } else{
            mView.onCityNotDeleted();
        }
    }
}
