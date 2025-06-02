package com.example.weathertrack.network.model;

public class ApiWeatherResponse {
    private double temperature;
    private int humidity;
    private String condition;

    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public String getCondition() { return condition; }
}
