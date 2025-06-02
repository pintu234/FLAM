package com.example.weathertrack.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.weathertrack.data.WeatherDao;
import com.example.weathertrack.data.WeatherDatabase;
import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.network.ApiClient;
import com.example.weathertrack.network.MockWeatherApiService;
import com.example.weathertrack.network.model.ApiWeatherResponse;
import com.example.weathertrack.util.NetworkUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private WeatherDao weatherDao;
    private LiveData<WeatherData> latestWeather;
    private Application application;
    private ExecutorService executorService;
    private MockWeatherApiService apiService;

    public WeatherRepository(Application application) {
        this.application = application;
        WeatherDatabase db = WeatherDatabase.getDatabase(application);
        weatherDao = db.weatherDao();
        latestWeather = weatherDao.getLatestWeather();
        executorService = Executors.newSingleThreadExecutor();
        apiService = ApiClient.getMockService();
    }

    public LiveData<WeatherData> getLatestWeather() {
        return latestWeather;
    }

    public LiveData<java.util.List<WeatherData>> getWeeklyWeather() {
        long now = System.currentTimeMillis();
        long sevenDaysAgo = now - 7L * 24 * 60 * 60 * 1000;
        return weatherDao.getWeatherSince(sevenDaysAgo);
    }

    public LiveData<java.util.List<WeatherData>> getWeatherForDay(long dayStart, long dayEnd) {
        return weatherDao.getWeatherForDay(dayStart, dayEnd);
    }

    public void fetchAndSaveWeather() {
        if (!NetworkUtils.isNetworkAvailable(application)) {
            Toast.makeText(application.getApplicationContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<ApiWeatherResponse> call = apiService.getTodayWeather();
        call.enqueue(new Callback<ApiWeatherResponse>() {
            @Override
            public void onResponse(Call<ApiWeatherResponse> call, Response<ApiWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiWeatherResponse apiRes = response.body();
                    long timestamp = System.currentTimeMillis();
                    WeatherData data = new WeatherData(timestamp, apiRes.getTemperature(), apiRes.getHumidity(), apiRes.getCondition());
                    insert(data);
                } else {
                    Toast.makeText(application.getApplicationContext(), "API returned error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiWeatherResponse> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), "Failed to fetch weather.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insert(WeatherData data) {
        executorService.execute(() -> {
            try {
                weatherDao.insert(data);
                // Optional: Clean up old entries older than 7 days
                long cutoff = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000;
                weatherDao.deleteOldEntries(cutoff);
            } catch (Exception e) {
                // Database error
                new android.os.Handler(application.getMainLooper()).post(() ->
                    Toast.makeText(application.getApplicationContext(), "Database error.", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
