package com.example.weatherdemoapp.presenter;

import com.example.weatherdemoapp.BuildConfig;
import com.example.weatherdemoapp.MyApplication;
import com.example.weatherdemoapp.model.Weather;
import com.example.weatherdemoapp.networking.WeatherAPI;
import io.reactivex.Observable;

public class WeatherPresenter {

    WeatherAPI weatherAPI;

    public WeatherPresenter(){
        weatherAPI = MyApplication.getRetrofitClient().create(WeatherAPI.class);
    }

    public Observable<Weather> getWeather(String city){
        return weatherAPI
                .getForecastWeather(
                        BuildConfig.API_KEY,
                        city,
                        7);
    }
}
