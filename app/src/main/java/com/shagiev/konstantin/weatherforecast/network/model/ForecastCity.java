package com.shagiev.konstantin.weatherforecast.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ForecastCity extends RealmObject implements Parcelable {

    @PrimaryKey
    private long id;

    private String cityName;

    public ForecastCity(){};

    public ForecastCity(long id, String cityName) {
        this.cityName = cityName;
        this.id=id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.cityName);
    }

    protected ForecastCity(Parcel in) {
        this.id = in.readLong();
        this.cityName = in.readString();
    }

    public static final Parcelable.Creator<ForecastCity> CREATOR = new Parcelable.Creator<ForecastCity>() {
        @Override
        public ForecastCity createFromParcel(Parcel source) {
            return new ForecastCity(source);
        }

        @Override
        public ForecastCity[] newArray(int size) {
            return new ForecastCity[size];
        }
    };
}