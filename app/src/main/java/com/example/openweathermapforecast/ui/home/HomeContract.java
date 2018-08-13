package com.example.openweathermapforecast.ui.home;

import android.support.annotation.NonNull;

import com.example.openweathermapforecast.models.Forecast;

import java.util.List;

public interface HomeContract {
    interface View {
        void showData(@NonNull List<Forecast> forecastList);
        void showError(@NonNull String errorMessage);
        void showProgress(boolean isVisible);
        void showTitle(@NonNull String title);
    }

    interface Presenter {
        void getForecast(@NonNull String cityId, @NonNull String apiKey);
        void stop();
    }
}
