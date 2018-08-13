
package com.example.openweathermapforecast.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {

    @SerializedName("3h")
    @Expose
    private float threeHourSummary;

    public float getThreeHourSummary() {
        return threeHourSummary;
    }

    public void setThreeHourSummary(float threeHourSummary) {
        this.threeHourSummary = threeHourSummary;
    }
}
