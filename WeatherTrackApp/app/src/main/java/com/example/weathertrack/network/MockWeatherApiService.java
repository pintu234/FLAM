package com.example.weathertrack.network;

import com.example.weathertrack.network.model.ApiWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MockWeatherApiService {
    @GET("/weather/today")
    Call<ApiWeatherResponse> getTodayWeather();
}
