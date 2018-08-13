package com.example.openweathermapforecast.network;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.dto.FiveDayForecast;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET(Constants.FIVE_DAY_FORECAST_PATH)
    Observable<FiveDayForecast> getFiveDayForecast(@Query("id") String cityId, @Query("APPID") String apiKey);
}
