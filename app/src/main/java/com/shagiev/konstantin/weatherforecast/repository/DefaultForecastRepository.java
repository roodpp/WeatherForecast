package com.shagiev.konstantin.weatherforecast.repository;

import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Daily;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class DefaultForecastRepository implements ForecastRepository{

    private synchronized void closeRealm(Realm realm) {
        if (realm != null && !realm.isClosed()) {
            realm.close();
            realm = null;
        }
    }

    private synchronized Realm openRealm(){
        return Realm.getDefaultInstance();
    }

    @Override
    public void addCity(final ForecastCity forecastCity){
        Realm realm = openRealm();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(forecastCity));
        closeRealm(realm);
    }

    @Override
    public void deleteCity(String city) {
        Realm realm = openRealm();
        realm.beginTransaction();
        ForecastCity forecastCity = realm.where(ForecastCity.class).equalTo("cityName", city).findFirst();
        if(forecastCity != null){
            forecastCity.deleteFromRealm();
        }
        realm.commitTransaction();
        closeRealm(realm);
    }

    @Override
    public long checkCitiesCount() {
        Realm realm = openRealm();
        long count = realm.where(ForecastCity.class).count();
        closeRealm(realm);
        return count;
    }

    @Override
    public void addCityList(final List<ForecastCity> forecastCities){
        Realm realm = openRealm();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(forecastCities));
        closeRealm(realm);
    }

    @Override
    public List<ForecastCity> getCities() {
        Realm realm = openRealm();
        List<ForecastCity> cities = realm.copyFromRealm(realm.where(ForecastCity.class).findAll());
        closeRealm(realm);
        return cities;
    }

    @Override
    public boolean isCityExist(String cityName) {
        boolean cityExist;
        Realm realm = openRealm();
        cityExist = realm.where(ForecastCity.class).equalTo("cityName", cityName).findFirst() != null;
        closeRealm(realm);
        return cityExist;
    }

    @Override
    public void addWeather(Daily daily) {
        Realm realm = openRealm();
        realm.executeTransaction(realm1 -> {realm1.delete(Day.class); realm.insert(daily.getList());});
        closeRealm(realm);
    }

    @Override
    public Observable<List<Day>> getWeather() {
        Realm realm = openRealm();
        RealmResults<Day> realmResults = realm.where(Day.class).findAll();
        List<Day> list = realm.copyFromRealm(realmResults);
        closeRealm(realm);
        return Observable.just(list);
    }


}
