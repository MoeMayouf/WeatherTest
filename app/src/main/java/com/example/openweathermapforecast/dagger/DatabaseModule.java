package com.example.openweathermapforecast.dagger;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.database.WeatherDatabase;
import com.example.openweathermapforecast.repo.DataSource;
import com.example.openweathermapforecast.repo.LocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DatabaseModule {

    @Provides
    @Singleton
    public WeatherDatabase provideWeatherDatabase(Context context) {
        return Room.databaseBuilder(context, WeatherDatabase.class, Constants.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    @Local
    public DataSource provideLocalDataSource(WeatherDatabase weatherDatabase) {
        return new LocalDataSource(weatherDatabase);
    }
}
