package com.example.openweathermapforecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.openweathermapforecast.dto.FiveDayForecast;

import io.reactivex.Maybe;

@Dao
public interface FiveDayForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void storeFiveDayForecast(FiveDayForecast... fiveDayForecasts);

    @Query("SELECT * FROM fiveDayForecastTable")
    Maybe<FiveDayForecast> getFiveDayForecast();
}
