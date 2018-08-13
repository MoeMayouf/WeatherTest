package com.example.openweathermapforecast;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.dto.FiveDayForecast;
import com.example.openweathermapforecast.repo.LocalDataSource;
import com.example.openweathermapforecast.repo.RemoteDataSource;
import com.example.openweathermapforecast.repo.WeatherRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherRepositoryTest {

    @Mock
    private LocalDataSource localDataSource;

    @Mock
    private RemoteDataSource remoteDataSource;

    @Mock
    private FiveDayForecast fiveDayForecast;

    private Observable<FiveDayForecast> testObservable;

    private WeatherRepository weatherRepository;

    @Before
    public void setup() {
        testObservable = Observable.just(fiveDayForecast);
        when(localDataSource.getFiveDayForecast(anyString(), anyString())).thenReturn(testObservable);
        weatherRepository = new WeatherRepository(localDataSource, remoteDataSource);
    }

    @Test
    public void testGetFiveDayForecast_Success() {
        when(remoteDataSource.getFiveDayForecast(anyString(), anyString()))
                .thenReturn(testObservable);
        weatherRepository.getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY)
                .test().assertResult(fiveDayForecast);
        verify(remoteDataSource).getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        verify(localDataSource).getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        verify(localDataSource).storeFiveDayForecast(fiveDayForecast);
        verifyNoMoreInteractions(localDataSource, remoteDataSource);
    }

    @Test
    public void testGetFiveDayForecast_Error() {
        when(remoteDataSource.getFiveDayForecast(anyString(), anyString()))
                .thenReturn(Observable.error(new NullPointerException()));
        weatherRepository.getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        verify(remoteDataSource).getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        verify(localDataSource).getFiveDayForecast(Constants.MADRID_ID, Constants.API_KEY);
        verifyNoMoreInteractions(localDataSource, remoteDataSource);
    }
}
