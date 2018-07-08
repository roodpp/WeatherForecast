package com.shagiev.konstantin.weatherforecast.di.module;


import android.support.annotation.NonNull;

import com.shagiev.konstantin.weatherforecast.BuildConfig;
import com.shagiev.konstantin.weatherforecast.network.ApiKeyInterceptor;
import com.shagiev.konstantin.weatherforecast.network.ForecastService;
import com.shagiev.konstantin.weatherforecast.network.DefaultForecastNetwork;
import com.shagiev.konstantin.weatherforecast.repository.DefaultForecastRepository;
import com.shagiev.konstantin.weatherforecast.network.ForecastNetwork;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Provides
    @Singleton
    ForecastNetwork provideWeatherNetwork(
            @NonNull ForecastService weatherService, @NonNull ForecastRepository forecastRepository) {
        return new DefaultForecastNetwork(weatherService, forecastRepository);
    }

    @Provides
    @Singleton
    ForecastRepository provideWeatherRepository() {
        return new DefaultForecastRepository();
    }

    @Provides
    @Singleton
    ForecastService provideWeatherService(@NonNull Retrofit retrofit) {
        return retrofit.create(ForecastService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ApiKeyInterceptor())
                .build();
    }

}
