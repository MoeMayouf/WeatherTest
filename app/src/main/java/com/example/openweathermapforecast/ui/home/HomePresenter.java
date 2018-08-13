package com.example.openweathermapforecast.ui.home;

import android.support.annotation.NonNull;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.models.Forecast;
import com.example.openweathermapforecast.dto.Condition;
import com.example.openweathermapforecast.dto.FiveDayForecast;
import com.example.openweathermapforecast.repo.WeatherRepository;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private static final String IMAGE_FILE_EXTENSION = ".png";
    private static final String CELSIUS_EXTENSION = "°C";
    private static final float KELVIN_FACTOR = 273.15f;
    private static final String DATE_FORMAT = "h:m a EEEE";
    private static final String DECIMAL_FORMAT = "#.#";

    private final HomeContract.View homeView;
    private final WeatherRepository weatherRepository;
    private final CompositeDisposable compositeDisposable;

    private String cityName;

    public HomePresenter(HomeContract.View homeView, WeatherRepository weatherRepository) {
        this.homeView = homeView;
        this.weatherRepository = weatherRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getForecast(@NonNull String cityId, @NonNull String apiKey) {
        compositeDisposable.add(weatherRepository.getFiveDayForecast(cityId, apiKey)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::getTitleFromData)
                .map(this::getForecastFromData)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> homeView.showProgress(true))
                .doOnTerminate(() -> homeView.showProgress(false))
                .subscribe(result -> {
                    homeView.showTitle(cityName);
                    homeView.showData(result);
                }, this::processError));
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    private void getTitleFromData(FiveDayForecast fiveDayForecast) {
        if (fiveDayForecast != null && fiveDayForecast.getCity() != null && fiveDayForecast.getCity().getName() != null) {
            cityName = fiveDayForecast.getCity().getName();
        }
    }

    @NonNull
    private List<Forecast> getForecastFromData(@NonNull FiveDayForecast fiveDayForecast) {
        final List<Forecast> forecastList = new ArrayList<>();
        for (Condition condition : fiveDayForecast.getConditions()) {
            final Forecast forecast = new Forecast(getReadableDate(condition.getDt()),
                    getCompleteIconUrl(condition.getWeather().get(0).getIcon()),
                    getReadableTempInCelsius(condition.getMain().getTemp()),
                    condition.getWeather().get(0).getDescription());
            forecastList.add(forecast);
        }
        return forecastList;
    }

    @NonNull
    private String getReadableDate(long unixTime) {
        final long unixTimeInMilliseconds = unixTime * 1000L;
        final LocalDateTime localDateTime = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(unixTimeInMilliseconds), ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT)
                .withLocale(Locale.getDefault()));

    }

    @NonNull
    private String getCompleteIconUrl(String rawUrl) {
        return Constants.BASE_URL_ICON + rawUrl + IMAGE_FILE_EXTENSION;
    }

    @NonNull
    private String getReadableTempInCelsius(double tempInKelvin) {
        final DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat.format(tempInKelvin - KELVIN_FACTOR) + CELSIUS_EXTENSION;
    }

    private void processError(Throwable throwable) {
        if (throwable != null && throwable.getMessage() != null) {
            homeView.showError(throwable.getMessage());
        }
    }
}
