package com.shagiev.konstantin.weatherforecast.screen.cities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shagiev.konstantin.weatherforecast.R;
import com.shagiev.konstantin.weatherforecast.network.model.ForecastCity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<ForecastCity> mCities;

    private Callback mCallback;

    public void setCallback(Callback callback){
        mCallback = callback;
    }

    public CityAdapter(List<ForecastCity> cities) {
        this.mCities = cities;
    }


    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        ForecastCity city = mCities.get(position);
        holder.bind(city, mCallback);

    }

    public void changeDataSet(List<ForecastCity> cities) {
        mCities.clear();
        mCities.addAll(cities);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public interface Callback {
        void onDeleteClick(String city);
    }



    public class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.city_name)
        TextView cityName;
        @BindView(R.id.city_action)
        ImageView cityAction;

        public CityViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ForecastCity city, Callback callback) {
            cityName.setText(city.getCityName());
            cityAction.setImageResource(R.drawable.ic_remove_circle);
            cityAction.setOnClickListener(v -> {
                if (callback != null) {
                    mCallback.onDeleteClick(city.getCityName());
                }
            });

        }

    }


}