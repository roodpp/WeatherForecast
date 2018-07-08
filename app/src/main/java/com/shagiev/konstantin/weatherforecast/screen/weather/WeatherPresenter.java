package com.shagiev.konstantin.weatherforecast.screen.weather;

import com.shagiev.konstantin.weatherforecast.network.ForecastNetwork;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherPresenter {
    private ForecastNetwork mNetwork;
    private ForecastRepository mRepository;
    private WeatherView mView;
    private CompositeDisposable mCompositeDisposable;

    public WeatherPresenter(ForecastNetwork forecastNetwork, ForecastRepository forecastRepository) {
        mNetwork = forecastNetwork;
        mRepository = forecastRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void onAttach(WeatherView weatherView){
        mView =  weatherView;
    }

    public void onDetach(){
        mCompositeDisposable.clear();
        mView = null;
    }

    public void onAddCityClick(){
        mView.openCitiesActivity();
    }

    public void loadWeather(String query) {
        mCompositeDisposable.add(mNetwork.weather(query)
                .doOnSubscribe(disposable -> mView.showLoading())
                .doOnTerminate(mView::hideLoading)
                .subscribe(mView::showWeather, errorMessage -> mView.showError(errorMessage.getMessage())));
    }

}
