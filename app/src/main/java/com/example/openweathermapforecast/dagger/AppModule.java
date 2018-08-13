package com.example.openweathermapforecast.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    @Provides
    @Singleton
    public Context provideApplicationContext(Application application) {
        return application;
    }
}
