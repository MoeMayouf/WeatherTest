package com.example.openweathermapforecast.dagger;

import android.content.Context;

import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.network.WeatherService;
import com.example.openweathermapforecast.repo.DataSource;
import com.example.openweathermapforecast.repo.RemoteDataSource;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
class NetworkModule {
    private static final int TIMEOUT = 30;

    @Provides
    @Singleton
    public Cache provideCache(Context context) {
        return new Cache(context.getCacheDir(), Constants.CACHE_SIZE);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())/**/
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public WeatherService provideWeatherService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    @Singleton
    @Remote
    public DataSource provideRemoteDataSource(WeatherService weatherService) {
        return new RemoteDataSource(weatherService);
    }
}
