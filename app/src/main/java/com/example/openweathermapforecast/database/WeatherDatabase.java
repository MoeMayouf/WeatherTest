package com.example.openweathermapforecast.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.openweathermapforecast.dto.FiveDayForecast;

@Database(entities = {FiveDayForecast.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase{
    public abstract FiveDayForecastDao getFiveDayForecast();
}
