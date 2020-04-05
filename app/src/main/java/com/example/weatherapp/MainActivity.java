package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test=findViewById(R.id.test);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<List<Weather>> call =weatherApi.getWeather();
        call.enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if(!response.isSuccessful()){
                    test.setText("code:"+response.code());
                    return;
                }
                List<Weather> weathers=response.body();
                for(Weather weather:weathers){
                    String content="";
                    content+=weather.getmTimeStamp()+"\n";
                    content+=weather.getmMaxTemp()+"\n";
                    content+=weather.getmMinTemp()+"\n";
                    content+=weather.getmDescrip()+"\n\n";
                    test.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                test.setText(t.getMessage());

            }
        });
    }
}
