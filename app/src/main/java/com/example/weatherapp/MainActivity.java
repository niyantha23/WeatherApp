package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public int unixTime = (int) (System.currentTimeMillis() / 1000L);
    public int Day1Date, Day2Date, Day3Date, Day4Date, Day5Date;
    int TimeStamp, listtime;
    float min_temp, max_temp, original_temp, feel_temp, windSpeed, humidity, pressure;
    String description = "";
    String TimeStampS = "";
    String min_tempS = "";
    String max_tempS = "";
    String original_tempS = "";
    String feel_tempS = "";
    String pressureS = "";
    String humidityS = "";
    String windSpeedS = "";
    Weather position;
    List<Weather> dataToDisplay;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    public MyViewModel viewModel;
    public LinearLayout rootLayout;
    public String weekday, weekday2, weekday3, weekday4, weekday5;
    String location;
    EditText searchbox;
    ImageButton searchButton;
    ProgressBar progressBar;
    TextView locationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = "Chennai";
        initialize();
        progressBar.setVisibility(View.INVISIBLE);
        DownloadTask task = new DownloadTask();
        task.execute("https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=fc7a4df678d008f3db0aa92ea746fa75");
        progressBar.setVisibility(View.VISIBLE);
        StatusBarUtil.setTransparent(MainActivity.this);
//      Log.i("locUrl",location);
        addFragments();
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = searchbox.getText().toString();
//                Log.i("loc",location);
                if (TextUtils.isEmpty(searchbox.getText())) {
                    searchbox.setError("Please Enter Your City");
                } else {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                    searchbox.setText("");
                    DownloadTask task = new DownloadTask();
                    task.execute("https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=fc7a4df678d008f3db0aa92ea746fa75");
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
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
                    TimeStampS = listObject.getString("dt");
                    TimeStamp = Integer.parseInt(TimeStampS);
                    JSONObject mainObject = listObject.getJSONObject("main");
                    min_tempS = mainObject.getString("temp_min");
                    min_temp = Float.parseFloat(min_tempS);
                    max_tempS = mainObject.getString("temp_max");
                    max_temp = Float.parseFloat(max_tempS);
                    original_tempS = mainObject.getString("temp");
                    original_temp = Float.parseFloat(original_tempS);
                    feel_tempS = mainObject.getString("feels_like");
                    feel_temp = Float.parseFloat(feel_tempS);
                    pressureS = mainObject.getString("pressure");
                    pressure = Float.parseFloat(pressureS);
                    humidityS = mainObject.getString("humidity");
                    humidity = Float.parseFloat(humidityS);
                    JSONArray weatherArray = listObject.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    description = weatherObject.getString("description");
                    min_temp -= 273.15;
                    max_temp -= 273.15;
                    original_temp -= 273.15;
                    feel_temp -= 273.15;
                    JSONObject windObject = listObject.getJSONObject("wind");
                    windSpeedS = windObject.getString("speed");
                    windSpeed = Float.parseFloat(windSpeedS);
                    windSpeed *= 3.6;
                    Weather weathers = new Weather(TimeStamp, min_temp, max_temp, description, original_temp, feel_temp, pressure, humidity, windSpeed);
                    list.add(weathers);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("loc", location);
            findClosest(list);
            getDataToDisplay(list);
            addDataToViewModel();
            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
            if (viewPager.getCurrentItem() == 0 && page != null) {
                ((Day1Fragment) page).assignText();
            } else if (viewPager.getCurrentItem() == 1 && page != null) {
                ((Day2Fragment) page).assignText();
            } else if (viewPager.getCurrentItem() == 2 && page != null) {
                ((Day3Fragment) page).assignText();
            } else if (viewPager.getCurrentItem() == 3 && page != null) {
                ((Day4Fragment) page).assignText();
            } else if (viewPager.getCurrentItem() == 4 && page != null) {
                ((Day5Fragment) page).assignText();
            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
                    TabLayout.Tab tab = tabLayout.getTabAt(position);
                    if (viewPager.getCurrentItem() == 0 && page != null) {
                        ((Day1Fragment) page).assignText();
                    } else if (viewPager.getCurrentItem() == 1 && page != null) {
                        ((Day2Fragment) page).assignText();
                    } else if (viewPager.getCurrentItem() == 2 && page != null) {
                        ((Day3Fragment) page).assignText();
                    } else if (viewPager.getCurrentItem() == 3 && page != null) {
                        ((Day4Fragment) page).assignText();
                    } else if (viewPager.getCurrentItem() == 4 && page != null) {
                        ((Day5Fragment) page).assignText();
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            String location1 = location.substring(0, 1).toUpperCase() + location.substring(1).toLowerCase();
            locationView.setText(location1);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    void initialize() {
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.app_bar_layout);
        viewPager = findViewById(R.id.view_pager);
        rootLayout = findViewById(R.id.root_layout);
        searchbox = findViewById(R.id.search_text_box);
        searchButton = findViewById(R.id.search_button);
        progressBar = findViewById(R.id.progress_circular);
        locationView = findViewById(R.id.location);
        weekday2 = getDay(unixTime + 86400);
        weekday3 = getDay(unixTime + 172800);
        weekday4 = getDay(unixTime + 259200);
        weekday5 = getDay(unixTime + 345600);
    }
    void findClosest(List<Weather> list) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int timestamp = list.get(i).getmTimeStamp();
            int date = (timestamp);
            numbers.add(date);
        }
        int myNumber = unixTime;
        int distance = Math.abs(numbers.get(0) - myNumber);
        int idx = 0;
        for (int c = 1; c < numbers.size(); c++) {
            int cdistance = Math.abs(numbers.get(c) - myNumber);
            if (cdistance < distance) {
                idx = c;
                distance = cdistance;
            }
        }
        int theNumber = numbers.get(idx);
        Day1Date = (theNumber);
        Day2Date = (theNumber + 86400);
        Day3Date = (theNumber + 172800);
        Day4Date = (theNumber + 259200);
        Day5Date = (theNumber + 345600);
    }
    //to get the day from epoch time
    String getDay(int time) {
        return Instant.ofEpochSecond(time)
                .atZone(ZoneId.of("IST"))
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.US);
    }
    //to get final data to be displayed
    void getDataToDisplay(List<Weather> list) {
        dataToDisplay = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listtime = list.get(i).getmTimeStamp();
            position = list.get(i);
            if (Day1Date == list.get(i).getmTimeStamp()) {
                Weather day1 = new Weather(position.getmTimeStamp(), position.getmMinTemp(), position.getmMaxTemp(), position.getmDescrip(), position.getmOriginalTemp(), position.getmFeelTemp()
                        , position.getmPressure(), position.getmHumidity(), position.getmWindSpeed());
                dataToDisplay.add(day1);
            } else if (listtime == Day2Date) {
                Weather day2 = new Weather(position.getmTimeStamp(), position.getmMinTemp(), position.getmMaxTemp(), position.getmDescrip(), position.getmOriginalTemp(), position.getmFeelTemp()
                        , position.getmPressure(), position.getmHumidity(), position.getmWindSpeed());
                dataToDisplay.add(day2);
            } else if (listtime == Day3Date) {
                Weather day3 = new Weather(position.getmTimeStamp(), position.getmMinTemp(), position.getmMaxTemp(), position.getmDescrip(), position.getmOriginalTemp(), position.getmFeelTemp()
                        , position.getmPressure(), position.getmHumidity(), position.getmWindSpeed());
                dataToDisplay.add(day3);
            } else if (listtime == Day4Date) {
                Weather day4 = new Weather(position.getmTimeStamp(), position.getmMinTemp(), position.getmMaxTemp(), position.getmDescrip(), position.getmOriginalTemp(), position.getmFeelTemp()
                        , position.getmPressure(), position.getmHumidity(), position.getmWindSpeed());
                dataToDisplay.add(day4);
            } else if (listtime == Day5Date) {
                Weather day5 = new Weather(position.getmTimeStamp(), position.getmMinTemp(), position.getmMaxTemp(), position.getmDescrip(), position.getmOriginalTemp(), position.getmFeelTemp()
                        , position.getmPressure(), position.getmHumidity(), position.getmWindSpeed());
                dataToDisplay.add(day5);
            }
        }
    }

    void addDataToViewModel() {
        viewModel.setList(dataToDisplay);
    }

    void addFragments() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragments(new Day1Fragment(), "Today");
        viewPagerAdapter.AddFragments(new Day2Fragment(), weekday2);
        viewPagerAdapter.AddFragments(new Day3Fragment(), weekday3);
        viewPagerAdapter.AddFragments(new Day4Fragment(), weekday4);
        viewPagerAdapter.AddFragments(new Day5Fragment(), weekday5);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
