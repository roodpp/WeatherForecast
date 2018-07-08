package com.shagiev.konstantin.weatherforecast.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;
import com.shagiev.konstantin.weatherforecast.screen.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private final List<ForecastCity> mForecastCities;

    public ViewPagerAdapter(FragmentManager fm, List<ForecastCity> cities) {
        super(fm);
        this.mForecastCities = cities;
    }

    public void setData(List<ForecastCity> list){
        mForecastCities.clear();
        mForecastCities.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherFragment.newInstance(mForecastCities.get(position));
    }

    @Override
    public int getCount() {
        return mForecastCities.size();
    }
}