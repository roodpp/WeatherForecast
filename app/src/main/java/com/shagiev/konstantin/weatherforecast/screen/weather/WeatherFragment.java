package com.shagiev.konstantin.weatherforecast.screen.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.di.component.ActivityComponent;
import com.shagiev.konstantin.weatherforecast.di.component.FragmentComponent;
import com.shagiev.konstantin.weatherforecast.di.module.FragmentModule;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;
import com.shagiev.konstantin.weatherforecast.network.model.response.weather.Day;
import com.shagiev.konstantin.weatherforecast.screen.main.MainActivity;
import com.shagiev.konstantin.weatherforecast.utils.Constants;
import com.shagiev.konstantin.weatherforecast.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends Fragment implements WeatherView {

    @BindView(R.id.rv_city_weather)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_city_name)
    TextView mTvCityName;
    @BindView(R.id.iv_toolbar_add_city)
    ImageView mIvAddCity;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    WeatherPresenter mPresenter;
    private MainActivity mActivity;
    private ForecastCity mForecastCity;
    private WeatherAdapter mAdapter;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(ForecastCity forecastCity) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CITY_TAG, forecastCity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            this.mActivity = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForecastCity = getArguments().getParcelable(Constants.CITY_TAG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ActivityComponent component = mActivity.getActivityComponent();
        if (component != null) {
            FragmentComponent fragmentComponent = component.plusFragmentComponent(new FragmentModule());
            fragmentComponent.inject(this);
            ButterKnife.bind(this, view);
            mPresenter.onAttach(this);
        }
        init();
        addListenersAndWatchers();
        return view;
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new WeatherAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mTvCityName.setText(mForecastCity.getCityName());
        mPresenter.loadWeather(mForecastCity.getCityName());
    }

    private void addListenersAndWatchers() {
        mIvAddCity.setOnClickListener(v -> mPresenter.onAddCityClick());
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
            if (Utils.checkInternetConnection(getContext())) {
                mPresenter.loadWeather(mForecastCity.getCityName());
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void openCitiesActivity() {
        mActivity.startCitiesActivity();
    }

    @Override
    public void showWeather(List<Day> weather) {
        WeatherDiffUtilCallback weatherDiffUtilCallback =
                new WeatherDiffUtilCallback(mAdapter.getData(), weather);
        DiffUtil.DiffResult weatherDiffResult = DiffUtil.calculateDiff(weatherDiffUtilCallback);
        mAdapter.setData(weather);
        weatherDiffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    public void showLoading() {
        mActivity.showProgress();
    }

    @Override
    public void hideLoading() {
        mActivity.hideProgress();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
