package com.example.openweathermapforecast.app;

import android.app.Application;

import com.example.openweathermapforecast.dagger.AppComponent;
import com.example.openweathermapforecast.dagger.DaggerAppComponent;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class OpenWeatherMapForecast extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
