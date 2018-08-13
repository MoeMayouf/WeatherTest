package com.example.openweathermapforecast.repo;

import android.support.annotation.NonNull;

import com.example.openweathermapforecast.dagger.Local;
import com.example.openweathermapforecast.dagger.Remote;
import com.example.openweathermapforecast.dto.FiveDayForecast;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepository implements DataSource {

    private final DataSource localDataSource;
    private final DataSource remoteDataSource;

    @Inject
    public WeatherRepository(@Local DataSource localDataSource, @Remote DataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<FiveDayForecast> getFiveDayForecast(@NonNull String cityId, @NonNull String apiKey) {
        return remoteDataSource.getFiveDayForecast(cityId, apiKey)
                .doOnNext(this::storeFiveDayForecast)
                .onErrorResumeNext(localDataSource.getFiveDayForecast(cityId, apiKey));
    }

    @Override
    public void storeFiveDayForecast(@NonNull FiveDayForecast fiveDayForecast) {
        localDataSource.storeFiveDayForecast(fiveDayForecast);
    }
}
