package com.example.weatherdemoapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherdemoapp.MyApplication;
import com.example.weatherdemoapp.R;
import com.example.weatherdemoapp.model.Forecastday;
import com.example.weatherdemoapp.model.Weather;
import com.example.weatherdemoapp.presenter.WeatherPresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    WeatherPresenter weatherPresenter;
    @Inject
    WeatherAdapter weatherAdapter;
    ArrayList<Forecastday> forecastdayArrayList = new ArrayList<>();

    @BindView(R.id.progressLayout) LinearLayout llProgress;
    @BindView(R.id.weatherLayout) LinearLayout llWeather;
    @BindView(R.id.txtCityName) TextView txtCityName;
    @BindView(R.id.txtTodayDate) TextView txtTodayDate;
    @BindView(R.id.txtTodayTemperature) TextView txtTodayTemp;
    @BindView(R.id.ivTodayIcon) ImageView imgTodayIcon;
    @BindView(R.id.rvWeatherForecast) RecyclerView rvWeatherForecast;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApplication.getWeatherComponent().inject(this);
        rvWeatherForecast.setLayoutManager(new LinearLayoutManager(this));
        practiceRx();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // getWeatherForecast();
    }
    private void practiceRx(){
        String[] strings = new String[]{"nidhi","nirav","brijesh"};
        Observable<Integer> observable = Observable
                .fromArray(strings)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if(s.startsWith("n"))
                                return true;
                        else
                            return false;
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.length();
                    }
                });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer s) {
                Log.e("weather",s.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        Observable<Long> timer1 = Observable.timer(5, TimeUnit.SECONDS);
        Observable<Long> timer2 = Observable.timer(2, TimeUnit.SECONDS);

        Observable<String> zip = Observable.zip(timer1, timer2, new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long aLong, Long aLong2) {
                return "abc";
            }
        });
        zip.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e("weather",s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void getWeatherForecast(){
        llProgress.setVisibility(View.VISIBLE);
        Observable<Weather> weatherObservable = weatherPresenter.getWeather("Bangalore");
        Observer<Weather> weatherObserver = new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Weather weather) {
                Log.e("Weather",weather.getLocation().getName());
                txtCityName.setText(weather.getLocation().getName());
                txtTodayTemp.setText(String.valueOf(weather.getCurrent().getTempC()));
                txtTodayDate.setText(weather.getCurrent().getLastUpdated());
                forecastdayArrayList.addAll(weather.getForecast().getForecastday());
                weatherAdapter.setData(forecastdayArrayList);
                rvWeatherForecast.setAdapter(weatherAdapter);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Weather","Error " + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                llProgress.setVisibility(View.GONE);
                llWeather.setVisibility(View.VISIBLE);
            }
        };
        weatherObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherObserver);
    }
}
