package com.example.weathertrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.repository.WeatherRepository;

import java.util.Calendar;
import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository repository;
    private LiveData<WeatherData> latestWeather;
    private LiveData<List<WeatherData>> weeklyWeather;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        latestWeather = repository.getLatestWeather();
        weeklyWeather = repository.getWeeklyWeather();
    }

    public LiveData<WeatherData> getLatestWeather() { return latestWeather; }
    public LiveData<List<WeatherData>> getWeeklyWeather() { return weeklyWeather; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    public void refreshWeather() {
        repository.fetchAndSaveWeather();
    }

    public LiveData<List<WeatherData>> getWeatherForDay(int year, int month, int day) {
        Calendar calStart = Calendar.getInstance();
        calStart.set(year, month, day, 0, 0, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        long dayStart = calStart.getTimeInMillis();

        Calendar calEnd = Calendar.getInstance();
        calEnd.set(year, month, day, 23, 59, 59);
        calEnd.set(Calendar.MILLISECOND, 999);
        long dayEnd = calEnd.getTimeInMillis();

        return repository.getWeatherForDay(dayStart, dayEnd);
    }
}
