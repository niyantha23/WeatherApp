package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String description = "";
    String TimeStamp = "";
    String min_temp = "";
    String max_temp = "";
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task = new DownloadTask();
        task.execute("https://api.openweathermap.org/data/2.5/forecast?q=Chennai&appid=fc7a4df678d008f3db0aa92ea746fa75");


    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream input = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Weather> list = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                String listInfo = jsonObject.getString("list");
                JSONArray ListArray = new JSONArray(listInfo);
                for (int i = 0; i < ListArray.length(); i++) {
                    JSONObject listObject = ListArray.getJSONObject(i);
                    TimeStamp = listObject.getString("dt");
                    JSONObject mainObject = listObject.getJSONObject("main");
                    min_temp = mainObject.getString("temp_min");
                    max_temp = mainObject.getString("temp_max");
                    JSONArray weatherArray = listObject.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    description = weatherObject.getString("description");
                    Weather weathers = new Weather(TimeStamp, description, min_temp, max_temp);
                    list.add(weathers);
                    // Log.i("date",weathers.getmDescrip()+" "+weathers.getmMaxTemp()+" "+weathers.getmMinTemp()+" "+weathers.getmTimeStamp());
//                  Log.i("date",TimeStamp);
//                  Log.i("dec",description);
//                  Log.i("mt",min_temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i=0;i<list.size();i++)
            Log.i("date",list.get(i).getmTimeStamp());

        }
    }
}
