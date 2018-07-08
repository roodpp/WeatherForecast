package com.shagiev.konstantin.weatherforecast.screen.cities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shagiev.konstantin.weatherforecast.ForecastApplication;
import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.di.component.ActivityComponent;
import com.shagiev.konstantin.weatherforecast.di.module.ActivityModule;
import com.shagiev.konstantin.weatherforecast.network.model.response.CurrentWeather;
import com.shagiev.konstantin.weatherforecast.screen.ForecastActivity;
import com.shagiev.konstantin.weatherforecast.utils.Constants;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CitiesActivity extends ForecastActivity implements CitiesView, CityAdapter.Callback{

    @BindView(R.id.cities_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_city_name)
    EditText mEtCityName;
    @BindView(R.id.tv_no_internet)
    TextView mTvNoInternet;
    @BindView(R.id.iv_city_search)
    ImageView mIvCitySearch;
    @BindView(R.id.iv_city_add)
    ImageView mIvCityAdd;
    @BindView(R.id.tv_searched_city)
    TextView mTvSearchedCity;
    @BindView(R.id.ll_searched_city_container)
    LinearLayout mLlSearchedCity;

    private List<ForecastCity> mCitiesList = new ArrayList<>();

    private CityAdapter mCityAdapter;

    @Inject
    CitiesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        ButterKnife.bind(this);
        initComponent();
        mPresenter.onAttach(this);
        if (getIntent().getParcelableArrayListExtra(Constants.CITY_TAG) != null) {
            mCitiesList = getIntent().getParcelableArrayListExtra(Constants.CITY_TAG);
        } else {
            mPresenter.loadExistCities();
        }
        addListenersAndWatchers();
        setAdapters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    private void addListenersAndWatchers(){
        mIvCityAdd.setOnClickListener(v -> {
            CurrentWeather currentWeather = (CurrentWeather) mTvSearchedCity.getTag();
            if(currentWeather != null){
                mPresenter.onAddCityClick(currentWeather);
            }
        });
        mIvCitySearch.setOnClickListener(v -> mPresenter.onSearchClick(mEtCityName.getText().toString()));
        mEtCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    mIvCitySearch.setVisibility(View.VISIBLE);
                } else{
                    mIvCitySearch.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void initComponent() {
        ActivityComponent mActivityComponent =
                ForecastApplication.getAppComponent().plusActivityComponent(new ActivityModule());
        mActivityComponent.inject(this);
    }

    private void setAdapters() {
        mCityAdapter = new CityAdapter(mCitiesList);
        mCityAdapter.setCallback(this);
        mRecyclerView.setAdapter(mCityAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
    }


    @Override
    public void onDeleteClick(String city) {
        mPresenter.onDeleteCityClick(city);
    }

    @Override
    public void initExistCities(List<ForecastCity> cities) {
        mCitiesList = cities;
    }

    @Override
    public void showSearchedCity(CurrentWeather currentWeather) {
        mLlSearchedCity.setVisibility(View.VISIBLE);
        mTvSearchedCity.setTag(currentWeather);
        mTvSearchedCity.setText(currentWeather.getName());
    }

    @Override
    public void onCitiesListChange() {
        mCityAdapter.changeDataSet(mCitiesList);
    }

    @Override
    public void onCityNotDeleted() {
        Toast.makeText(this, getString(R.string.can_not_delete_city), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCityNotAdded() {
        Toast.makeText(this, getString(R.string.city_exist), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        showProgress();
        mLlSearchedCity.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        hideProgress();
        mLlSearchedCity.setEnabled(true);
    }

    @Override
    public void showError(String errorMessage) {
        mLlSearchedCity.setVisibility(View.INVISIBLE);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
