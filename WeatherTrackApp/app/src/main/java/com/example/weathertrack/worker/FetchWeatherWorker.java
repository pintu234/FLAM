package com.example.weathertrack.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weathertrack.repository.WeatherRepository;

public class FetchWeatherWorker extends Worker {
    private WeatherRepository repository;

    public FetchWeatherWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        repository = new WeatherRepository((android.app.Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        repository.fetchAndSaveWeather();
        return Result.success();
    }
}
