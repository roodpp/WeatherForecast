package com.shagiev.konstantin.weatherforecast.network;


import com.shagiev.konstantin.weatherforecast.network.model.response.CurrentWeather;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefaultForecastNetwork implements ForecastNetwork {

    private final ForecastService mWeatherService;
    private final ForecastRepository mRepository;

    public DefaultForecastNetwork(ForecastService weatherService, ForecastRepository forecastRepository) {
        mWeatherService = weatherService;
        mRepository = forecastRepository;
    }

    @Override
    public Observable<CurrentWeather> currentWeather(String cityName) {
        return mWeatherService.currentWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Day>> weather(String cityName) {
        return mWeatherService.weather(cityName)
                .delay(1, TimeUnit.SECONDS)
                .flatMap(daily -> {
                    mRepository.addWeather(daily);
                    return mRepository.getWeather();
                })
                .onErrorResumeNext(throwable -> {
                    return mRepository.getWeather();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
