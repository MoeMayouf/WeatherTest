package com.example.openweathermapforecast.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.openweathermapforecast.R;
import com.example.openweathermapforecast.app.OpenWeatherMapForecast;
import com.example.openweathermapforecast.common.Constants;
import com.example.openweathermapforecast.models.Forecast;
import com.example.openweathermapforecast.repo.WeatherRepository;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @Inject
    WeatherRepository weatherRepository;


    private ProgressBar progressBar;

    private HomePresenter homePresenter;

    private ItemAdapter<Forecast> itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pb_progress);
        final RecyclerView recyclerView = findViewById(R.id.rv_forecast_list);

        ((OpenWeatherMapForecast)getApplication()).getAppComponent()
                .inject(this);

        itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(fastAdapter);

        homePresenter = new HomePresenter(this, weatherRepository);
    }

    @Override
    protected void onStart() {
        super.onStart();
        homePresenter.getForecast(Constants.MADRID_ID, Constants.API_KEY);
    }

    @Override
    public void showData(@NonNull List<Forecast> forecastList) {
        itemAdapter.add(forecastList);
    }

    @Override
    public void showError(@NonNull String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override
    protected void onStop() {
        super.onStop();
        homePresenter.stop();
    }
}
