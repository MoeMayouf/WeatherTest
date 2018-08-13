package com.example.openweathermapforecast.database;

import android.arch.persistence.room.TypeConverter;

import com.example.openweathermapforecast.dto.Condition;
import com.example.openweathermapforecast.dto.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class FiveDayForecastTypeConverters {

    @TypeConverter
    public static List<Condition> toListOfConditions(String rawValue) {
        if (rawValue == null) {
            return Collections.emptyList();
        }
        final Type type = new TypeToken<List<Condition>>() {}.getType();
        return new Gson().fromJson(rawValue, type);
    }

    @TypeConverter
    public static String fromListOfConditions(List<Condition> conditionList) {
        return new Gson().toJson(conditionList);
    }

    @TypeConverter
    public static List<Weather> toListOfWeather(String rawValue) {
        if (rawValue == null) {
            return Collections.emptyList();
        }
        final Type type = new TypeToken<List<Weather>>() {}.getType();
        return new Gson().fromJson(rawValue, type);
    }

    @TypeConverter
    public static String fromListOfWeather(List<Weather> weatherList) {
        return new Gson().toJson(weatherList);
    }
}
