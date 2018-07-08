package com.shagiev.konstantin.weatherforecast.screen.weather;


import android.support.v7.util.DiffUtil;

import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;

import java.util.List;

public class WeatherDiffUtilCallback extends DiffUtil.Callback {

    private final List<Day> mOldList;
    private final List<Day> mNewList;

    public WeatherDiffUtilCallback(List<Day> oldList, List<Day> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Day oldDay = mOldList.get(oldItemPosition);
        Day newDay = mNewList.get(newItemPosition);
        return newDay.getDt() == oldDay.getDt();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Day oldDay = mOldList.get(oldItemPosition);
        Day newDay = mNewList.get(newItemPosition);
        return oldDay.getTemp().getMax() == newDay.getTemp().getMax()
                && oldDay.getTemp().getMin() == newDay.getTemp().getMin();
    }
}
