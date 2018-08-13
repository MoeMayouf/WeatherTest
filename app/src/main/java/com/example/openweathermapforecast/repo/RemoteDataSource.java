package com.example.openweathermapforecast.repo;

import android.support.annotation.NonNull;

import com.example.openweathermapforecast.dto.FiveDayForecast;
import com.example.openweathermapforecast.network.WeatherService;

import io.reactivex.Observable;

public class RemoteDataSource implements DataSource {

    private final WeatherService weatherService;

    public RemoteDataSource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public Observable<FiveDayForecast> getFiveDayForecast(@NonNull String cityId, @NonNull String apiKey) {
        return weatherService.getFiveDayForecast(cityId, apiKey);
    }

    @Override
    public void storeFiveDayForecast(@NonNull FiveDayForecast fiveDayForecast) {
        //no-op
    }
}
