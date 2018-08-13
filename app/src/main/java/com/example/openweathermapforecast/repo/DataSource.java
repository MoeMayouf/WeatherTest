package com.example.openweathermapforecast.repo;

import android.support.annotation.NonNull;

import com.example.openweathermapforecast.dto.FiveDayForecast;

import io.reactivex.Observable;


public interface DataSource {
    Observable<FiveDayForecast> getFiveDayForecast(@NonNull String cityId, @NonNull String apiKey);

    void storeFiveDayForecast(@NonNull FiveDayForecast fiveDayForecast);
}
