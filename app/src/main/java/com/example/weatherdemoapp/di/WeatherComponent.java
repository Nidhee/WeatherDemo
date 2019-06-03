package com.example.weatherdemoapp.di;

import com.example.weatherdemoapp.view.MainActivity;

import dagger.Component;

@Component (modules = WeatherModule.class)
public interface  WeatherComponent {
     void inject(MainActivity activity);
}
