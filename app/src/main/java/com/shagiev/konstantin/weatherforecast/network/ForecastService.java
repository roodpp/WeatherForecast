package com.shagiev.konstantin.weatherforecast.network;


import android.support.annotation.NonNull;

import com.shagiev.konstantin.weatherforecast.network.model.response.CurrentWeather;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Daily;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {
    @GET("data/2.5/forecast/daily?units=metric&cnt=7")
    Observable<Daily> weather(@NonNull @Query("q") String query);


    @GET("data/2.5/weather?units=metric")
    Observable<CurrentWeather> currentWeather(@NonNull @Query("q") String query);

}
