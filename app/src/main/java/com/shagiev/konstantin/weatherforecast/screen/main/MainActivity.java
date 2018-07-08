package com.shagiev.konstantin.weatherforecast.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.shagiev.konstantin.weatherforecast.ForecastApplication;
import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.di.component.ActivityComponent;
import com.shagiev.konstantin.weatherforecast.di.module.ActivityModule;
import com.shagiev.konstantin.weatherforecast.screen.ForecastActivity;
import com.shagiev.konstantin.weatherforecast.utils.Constants;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;
import com.shagiev.konstantin.weatherforecast.screen.cities.CitiesActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ForecastActivity implements MainView {

    @BindView(R.id.weather_view_pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;
    private ActivityComponent mActivityComponent;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        ButterKnife.bind(this);
        mPresenter.onAttach(this);
        mPresenter.loadCitiesList();
    }

    private void initComponent() {
        mActivityComponent =
                ForecastApplication.getAppComponent().plusActivityComponent(new ActivityModule());
        mActivityComponent.inject(this);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void setViewPagerAdapter(List<ForecastCity> cities) {
        if(mViewPagerAdapter != null){
            mViewPagerAdapter.setData(cities);
        } else{
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), cities);
        }
        mViewPager.setOffscreenPageLimit(cities.size());
        mViewPager.setPageMargin(10);
        mViewPager.setPageMarginDrawable(new ColorDrawable(Color.parseColor("#000000")));
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    public void startCitiesActivity(){
        Intent intent = new Intent(this, CitiesActivity.class);
        // intent.putParcelableArrayListExtra(Constants.CITY_TAG, mForecastCities);
        startActivityForResult(intent, Constants.NEW_CITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.NEW_CITY_CODE) {
                mPresenter.loadCitiesList();
            }
        }
    }

    @Override
    public void initCitiesList(List<ForecastCity> cities) {
        if (cities.size() > 0) {
            setViewPagerAdapter(cities);
        } else {
            mPresenter.onCitiesListEmpty();
        }
    }
}
