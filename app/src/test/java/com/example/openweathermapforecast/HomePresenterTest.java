package com.example.openweathermapforecast;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.dto.City;
import com.example.openweathermapforecast.dto.Condition;
import com.example.openweathermapforecast.dto.FiveDayForecast;
import com.example.openweathermapforecast.dto.Main;
import com.example.openweathermapforecast.dto.Weather;
import com.example.openweathermapforecast.models.Forecast;
import com.example.openweathermapforecast.repo.WeatherRepository;
import com.example.openweathermapforecast.ui.home.HomeContract;
import com.example.openweathermapforecast.ui.home.HomePresenter;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    private static final String CITY_NAME = "Madrid";
    private static final String ICON_NAME = "icon65";
    private static final String WEATHER_CONDITION = "sunny";
    private static final String EPOCH_ZERO_READABLE_DATE = "5:30 AM Thursday";
    private static final String ZERO_DEGREES = "0°C";
    private static final String ERROR_MESSAGE = "Error: Unknown";

    @Mock
    private HomeContract.View homeView;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private City city;

    @Mock
    private Condition condition;

    @Mock
    private Weather weather;

    @Mock
    private Main main;

    @Mock
    private FiveDayForecast fiveDayForecast;

    @Captor
    private ArgumentCaptor<List<Forecast>> forecastCaptor;
    @Captor
    private ArgumentCaptor<String> throwableCaptor;

    private InOrder inOrder;
    private HomePresenter homePresenter;

    @BeforeClass
    public static void setRxSchedulers() {
        final Scheduler scheduler = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> scheduler);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> scheduler);
    }

    @Before
    public void setup() {

        //Weather Repository
        when(weatherRepository.getFiveDayForecast(anyString(), anyString()))
                .thenReturn(Observable.just(fiveDayForecast));

        //FiveDayForecast
        when(fiveDayForecast.getCity()).thenReturn(city);
        when(fiveDayForecast.getConditions()).thenReturn(Collections.singletonList(condition));

        //City
        when(city.getName()).thenReturn(CITY_NAME);

        //Condition
        when(condition.getDt()).thenReturn(0);
        when(condition.getMain()).thenReturn(main);
        when(condition.getWeather()).thenReturn(Collections.singletonList(weather));

        //Main
        when(main.getTemp()).thenReturn(273.15f);

        //Weather
        when(weather.getIcon()).thenReturn(ICON_NAME);
        when(weather.getDescription()).thenReturn(WEATHER_CONDITION);

        inOrder = inOrder(weatherRepository, homeView);
        homePresenter = new HomePresenter(homeView, weatherRepository);
    }

    @Test
    public void testGetForecast_Success() {
        homePresenter.getForecast(Constants.MADRID_ID, Constants.API_KEY);
        inOrder.verify(weatherRepository).getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        inOrder.verify(homeView).showProgress(true);
        inOrder.verify(homeView).showTitle(CITY_NAME);
        inOrder.verify(homeView).showData(forecastCaptor.capture());
        inOrder.verify(homeView).showProgress(false);
        inOrder.verifyNoMoreInteractions();
        final Forecast forecast = forecastCaptor.getValue().get(0);
        assertEquals(ZERO_DEGREES, forecast.getTemperature());
        assertEquals(Constants.BASE_URL_ICON + ICON_NAME + ".png", forecast.getIconUrl());
        assertEquals(WEATHER_CONDITION, forecast.getCondition());
        assertEquals(EPOCH_ZERO_READABLE_DATE, forecast.getDay());
    }

    @Test
    public void testGetForecast_Error() {
        when(weatherRepository.getFiveDayForecast(anyString(), anyString()))
                .thenReturn(Observable.error(new IllegalStateException(ERROR_MESSAGE)));
        homePresenter.getForecast(Constants.MADRID_ID, Constants.API_KEY);
        inOrder.verify(weatherRepository).getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        inOrder.verify(homeView).showProgress(true);
        inOrder.verify(homeView).showProgress(false);
        inOrder.verify(homeView).showError(ERROR_MESSAGE);
        inOrder.verifyNoMoreInteractions();
    }

    @AfterClass
    public static void resetRxSchedulers() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

}
