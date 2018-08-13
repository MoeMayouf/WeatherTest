package com.example.openweathermapforecast.repo;

import android.support.annotation.NonNull;

import com.example.openweathermapforecast.database.WeatherDatabase;
import com.example.openweathermapforecast.dto.FiveDayForecast;

import io.reactivex.Observable;

public class LocalDataSource implements DataSource {

    private final WeatherDatabase weatherDatabase;

    public LocalDataSource(WeatherDatabase weatherDatabase) {
        this.weatherDatabase = weatherDatabase;
    }

    @Override
    public Observable<FiveDayForecast> getFiveDayForecast(@NonNull String cityId, @NonNull String apiKey) {
        return weatherDatabase.getFiveDayForecast().getFiveDayForecast().toObservable();
    }

    @Override
    public void storeFiveDayForecast(@NonNull FiveDayForecast fiveDayForecast) {
        weatherDatabase.getFiveDayForecast().storeFiveDayForecast(fiveDayForecast);
    }
}
