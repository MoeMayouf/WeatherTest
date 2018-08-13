package com.example.openweathermapforecast.dagger;


import android.app.Application;

import com.example.openweathermapforecast.ui.home.HomeActivity;
import com.example.openweathermapforecast.repo.WeatherRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, NetworkModule.class})
public interface AppComponent {

    WeatherRepository weatherRepository();
    void inject(HomeActivity homeActivity);

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance Builder application(Application application);
    }
}
