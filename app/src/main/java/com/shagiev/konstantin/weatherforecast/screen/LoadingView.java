package com.shagiev.konstantin.weatherforecast.screen;


public interface LoadingView {

    void showLoading();

    void hideLoading();

    void showError(String errorMessage);

}
