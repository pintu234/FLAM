package com.example.weathertrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_table")
public class WeatherData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long timestamp; // epoch millis
    private double temperature;
    private int humidity;
    private String condition;

    public WeatherData(long timestamp, double temperature, int humidity, String condition) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.condition = condition;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public long getTimestamp() { return timestamp; }
    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public String getCondition() { return condition; }
}
