package com.shagiev.konstantin.weatherforecast.screen.weather;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;
import com.shagiev.konstantin.weatherforecast.utils.ForecastDateUtils;
import com.shagiev.konstantin.weatherforecast.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_date)
    TextView mTvDate;

    @BindView(R.id.tv_weather_description)
    TextView mTvWeatherDescription;

    @BindView(R.id.tv_high_temperature)
    TextView mTvHighTemperature;

    @BindView(R.id.tv_low_temperature)
    TextView mTvLowTemperature;

    @BindView(R.id.iv_weather_icon)
    ImageView mIvWeatherIcon;

    public WeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Day day) {
        long normalizedDate = ForecastDateUtils.normalizeDate(day.getDt()*1000);
        String friendlyDate = ForecastDateUtils.getFriendlyDateString(itemView.getContext(), normalizedDate, true);
        mTvDate.setText(friendlyDate);
        mIvWeatherIcon.setImageDrawable(itemView.getContext().getResources().getDrawable(Utils.getArtResourceWeather(day.getWeather().get(0).getId())));
        if (day.getTemp() != null) {
            String highTemp = mTvHighTemperature.getResources().getString(R.string.f_temperature, (int) day.getTemp().getMax());
            String lowTemp = mTvLowTemperature.getResources().getString(R.string.f_temperature, (int) day.getTemp().getMin());
            mTvHighTemperature.setText(highTemp);
            mTvLowTemperature.setText(lowTemp);
        }
        if(day.getWeather() != null && day.getWeather().size() > 0){
            mTvWeatherDescription.setText(day.getWeather().get(0).getDescription());
        }
    }
}
