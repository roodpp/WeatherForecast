package com.shagiev.konstantin.weatherforecast.screen.weather;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    private final List<Day> mDays;

    public WeatherAdapter(List<Day> days) {
        mDays = days;
    }

    public List<Day> getData(){
        return mDays;
    }

    public void setData(List<Day> days) {
        mDays.clear();
        mDays.addAll(days);
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        Day day = mDays.get(position);
        holder.bind(day);
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }
}
