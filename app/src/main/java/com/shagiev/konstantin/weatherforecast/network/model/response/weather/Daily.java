
package com.shagiev.konstantin.weatherforecast.network.model.response.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Daily extends RealmObject {

    @SerializedName("city")
    @Expose
    private DayCity city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private RealmList<Day> list = null;

    public DayCity getCity() {
        return city;
    }

    public void setCity(DayCity city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public RealmList<Day> getList() {
        return list;
    }

    public void setList(RealmList<Day> list) {
        this.list = list;
    }

}
