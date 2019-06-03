package com.example.weatherdemoapp.networking;

import com.example.weatherdemoapp.model.Weather;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("forecast.json")
    Observable<Weather> getForecastWeather(@Query("key") String key, @Query("q") String city, @Query("days") int days);
}
