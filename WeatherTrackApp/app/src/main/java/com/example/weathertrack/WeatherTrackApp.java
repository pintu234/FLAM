package com.example.weathertrack;

import android.app.Application;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.weathertrack.worker.FetchWeatherWorker;

import java.util.concurrent.TimeUnit;

public class WeatherTrackApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        scheduleWeatherWork();
    }

    private void scheduleWeatherWork() {
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                FetchWeatherWorker.class,
                6, TimeUnit.HOURS)
                .addTag("weather_fetch_work")
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "WeatherFetchWork",
                ExistingPeriodicWorkPolicy.KEEP,
                request);
    }
}
