package com.example.weatherdemoapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherdemoapp.R;
import com.example.weatherdemoapp.model.Forecastday;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private ArrayList<Forecastday> forecastdays;
    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Forecastday> forecastdayArrayList){
        forecastdays = forecastdayArrayList;
    }

    @Override
    public int getItemCount() {
        return forecastdays.size();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View weatherView = layoutInflater.inflate(R.layout.row_weather,parent,false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(weatherView);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {

        Forecastday forecastday = forecastdays.get(position);
        holder.txtDay.setText(forecastday.getDate());
        holder.txtTemp.setText(String.valueOf(forecastday.getDay().getAvgtempC()));
        Glide.with(context)
                .load("https:"+forecastday.getDay().getCondition().getIcon())
                .into(holder.icon);
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView txtDay;
        TextView txtTemp;
        ImageView icon;

        public WeatherViewHolder(View view) {
            super(view);
            txtDay = view.findViewById(R.id.txtDay);
            txtTemp = view.findViewById(R.id.txtTemperature);
            icon = view.findViewById(R.id.txtIcon);
        }
    }
}
