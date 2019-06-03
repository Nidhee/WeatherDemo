package com.example.weatherdemoapp.di;

import android.content.Context;

import com.example.weatherdemoapp.presenter.WeatherPresenter;
import com.example.weatherdemoapp.view.WeatherAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {
    private Context context;

    public  WeatherModule(Context context){
        this.context = context;
    }
    @Provides
    WeatherPresenter provideWeatherPresenter(){
        return new WeatherPresenter();
    }
    @Provides
    WeatherAdapter provideWeatherAdapter(){
        return new WeatherAdapter(context);
    }


}
