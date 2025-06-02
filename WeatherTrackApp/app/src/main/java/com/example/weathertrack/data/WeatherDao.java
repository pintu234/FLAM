package com.example.weathertrack.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weathertrack.model.WeatherData;

import java.util.List;

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WeatherData data);

    @Query("SELECT * FROM weather_table WHERE timestamp >= :startTime ORDER BY timestamp ASC")
    LiveData<List<WeatherData>> getWeatherSince(long startTime);

    @Query("SELECT * FROM weather_table ORDER BY timestamp DESC LIMIT 1")
    LiveData<WeatherData> getLatestWeather();

    @Query("SELECT * FROM weather_table WHERE timestamp BETWEEN :dayStart AND :dayEnd ORDER BY timestamp ASC")
    LiveData<List<WeatherData>> getWeatherForDay(long dayStart, long dayEnd);

    @Query("DELETE FROM weather_table WHERE timestamp < :cutoffTime")
    void deleteOldEntries(long cutoffTime);
}
