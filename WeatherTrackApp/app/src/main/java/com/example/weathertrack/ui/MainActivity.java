package com.example.weathertrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.viewmodel.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private WeatherViewModel viewModel;
    private TextView tvLatestTemp, tvLatestCondition;
    private ProgressBar progressBar;
    private Button btnRefresh;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatestTemp = findViewById(R.id.tvLatestTemp);
        tvLatestCondition = findViewById(R.id.tvLatestCondition);
        progressBar = findViewById(R.id.progressBar);
        btnRefresh = findViewById(R.id.btnRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        observeLatestWeather();
        observeWeeklyWeather();

        btnRefresh.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.refreshWeather();
        });

        adapter.setOnItemClickListener(data -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(data.getTimestamp());
            Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
            intent.putExtra("timestamp", data.getTimestamp());
            startActivity(intent);
        });
    }

    private void observeLatestWeather() {
        viewModel.getLatestWeather().observe(this, data -> {
            if (data != null) {
                tvLatestTemp.setText(String.format(Locale.getDefault(), "%.1f°C", data.getTemperature()));
                tvLatestCondition.setText(data.getCondition());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void observeWeeklyWeather() {
        viewModel.getWeeklyWeather().observe(this, weatherDataList -> {
            if (weatherDataList != null) {
                adapter.setWeatherList(weatherDataList);
            }
        });
    }
}
