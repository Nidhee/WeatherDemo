package com.example.weatherdemoapp;

import com.example.weatherdemoapp.base.BasePresenter;
import com.example.weatherdemoapp.base.BaseView;

public class WeatherContract {

interface IWeatherView extends BaseView{

    void showLoading();
    void hideLoading();
    void showWeatherData();
    void showForecastList();
    void showNoResult();
}
interface  IWeatherPresenter extends BasePresenter{

}

}
