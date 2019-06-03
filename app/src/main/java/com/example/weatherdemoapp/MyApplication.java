package com.example.weatherdemoapp;

import android.app.Application;

import com.example.weatherdemoapp.di.DaggerWeatherComponent;
import com.example.weatherdemoapp.di.WeatherComponent;
import com.example.weatherdemoapp.di.WeatherModule;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static Retrofit retrofitClient = null;

    public static WeatherComponent getWeatherComponent() {
        return weatherComponent;
    }

    private  static WeatherComponent weatherComponent = null;
    @Override
    public void onCreate() {
        super.onCreate();
        weatherComponent = DaggerWeatherComponent.builder()
                .weatherModule(new WeatherModule(this))
                .build();
    }

    public static Retrofit getRetrofitClient(){

        if(retrofitClient==null){

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build();

            retrofitClient =  new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.SERVER_URL)
                    .build();
        }
        return retrofitClient;
    }
}
