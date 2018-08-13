
package com.example.openweathermapforecast.dto;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.database.FiveDayForecastTypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = Constants.TABLE_NAME)
@TypeConverters(FiveDayForecastTypeConverters.class)
public class FiveDayForecast {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private float message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private List<Condition> conditions = null;
    @Embedded
    @SerializedName("city")
    @Expose
    private City city;

    public int getPrimaryKey() {
        return primaryKey;
    }

    public String getCod() {
        return cod;
    }

    public float getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public City getCity() {
        return city;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
