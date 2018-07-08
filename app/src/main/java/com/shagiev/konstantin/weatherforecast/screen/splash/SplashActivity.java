package com.shagiev.konstantin.weatherforecast.screen.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shagiev.konstantin.weatherforecast.ForecastApplication;
import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.repository.ForecastRepository;
import com.shagiev.konstantin.weatherforecast.utils.Constants;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;
import com.shagiev.konstantin.weatherforecast.utils.Utils;
import com.shagiev.konstantin.weatherforecast.screen.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {

    @Inject
    ForecastRepository mForecastRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ForecastApplication.getAppComponent().injectSplashActivity(this);
        checkFirstRun();
    }

    public void checkFirstRun() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.getBoolean(Constants.IS_FIRST_RUN, true)) {
            if (Utils.checkInternetConnection(getApplicationContext())) {
                mForecastRepository.addCityList(initCitiesList());
                prefs.edit().putBoolean(Constants.IS_FIRST_RUN, false).apply();
                startActivity();

            } else {
                Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        } else {
            startActivity();
        }
    }

    private List<ForecastCity> initCitiesList(){
        List<ForecastCity> cities = new ArrayList<>();
        ForecastCity moscowCity = new ForecastCity(524901, "Moscow");
        ForecastCity newYorkCity = new ForecastCity(5128581, "New York");
        ForecastCity londonCity = new ForecastCity(2643743, "London");
        ForecastCity parisCity = new ForecastCity(2988507, "Paris");
        cities.add(newYorkCity);
        cities.add(londonCity);
        cities.add(parisCity);
        cities.add(moscowCity);
        return cities;
    }


    public void startActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 500);

    }
}
