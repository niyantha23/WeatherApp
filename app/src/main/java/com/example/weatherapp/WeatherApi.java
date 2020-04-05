package com.example.weatherapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApi {
    @GET("forecast?q=Chennai&appid=fc7a4df678d008f3db0aa92ea746fa75")
    Call<List<Weather>> getWeather();
}
