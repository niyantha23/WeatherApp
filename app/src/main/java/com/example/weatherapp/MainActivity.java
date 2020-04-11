package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public int unixTime = (int) (System.currentTimeMillis() / 1000L);
    public int Day1Date;
    public int Day2Date;
    public int Day3Date;
    public int Day4Date;
    public int Day5Date;
    int TimeStamp;
    String description = "";
    String TimeStampS = "";
    String min_temp = "";
    String max_temp = "";
    int listtime;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    public MyViewModel viewModel;
    private String Day1MinTemp="";
    private String Day1MaxTemp;
    private String Day1Descrip;
    private String Day2MinTemp;
    private String Day2MaxTemp;
    private String Day2Descrip;
    private String Day3MinTemp;
    private String Day3MaxTemp;
    private String Day3Descrip;
    private String Day4MinTemp;
    private String Day4MaxTemp;
    private String Day4Descrip;
    private String Day5MinTemp;
    private String Day5MaxTemp;
    private String Day5Descrip;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        DownloadTask task = new DownloadTask();
        task.execute("https://api.openweathermap.org/data/2.5/forecast?q=Chennai&appid=fc7a4df678d008f3db0aa92ea746fa75");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragments(new Day1Fragment(),"Day1");
        viewPagerAdapter.AddFragments(new Day2Fragment(), "Day2");
        viewPagerAdapter.AddFragments(new Day3Fragment(), "Day3");
        viewPagerAdapter.AddFragments(new Day4Fragment(), "Day4");
        viewPagerAdapter.AddFragments(new Day5Fragment(), "Day5");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        //date=findViewById(R.id.day1_date);


        Log.i("time", unixTime + "");
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
                    TimeStamp=Integer.parseInt(TimeStampS);

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
            findClosest(list);
            getDataToDisplay(list);
            addDataToViewModel();
            //Log.i("D1", viewModel.getDay1Descrip());
           // Log.i("D2", Day2Descrip);
//            Log.i("D3", Day3Date);
//            Log.i("D4", Day4Date);
//            Log.i("D5", Day5Date);
         //date.setText(Day1Date+"");
            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" +"0");
            Day1Fragment day1Fragment= (Day1Fragment) page;
            day1Fragment.assignText();

        }
    }

    void initialize() {
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.app_bar_layout);
        viewPager = findViewById(R.id.view_pager);
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

        Log.i("NOW", unixTime + "");
      Log.i("CLOSEST", theNumber + "");
//        Log.i("D1", Day1Date);
//        Log.i("D2", Day2Date);
//        Log.i("D3", Day3Date);
//        Log.i("D4", Day4Date);
//        Log.i("D5", Day5Date);




    }

    //to get final data to be displayed
    void getDataToDisplay(List<Weather> list) {

        for (int i = 0; i < list.size(); i++) {

            listtime = list.get(i).getmTimeStamp();
            if (Day1Date==list.get(i).getmTimeStamp()) {

                Day1MinTemp = list.get(i).getmMinTemp();
                //date.setText(list.get(i).getmDescrip());
                Day1MaxTemp = list.get(i).getmMaxTemp();
                Day1Descrip = list.get(i).getmDescrip();
                Log.i("closest equal",list.get(i).getmDescrip());
            } else if (listtime == Day2Date) {
                Day2MinTemp = list.get(i).getmMinTemp();
                Day2MaxTemp = list.get(i).getmMaxTemp();
                Day2Descrip = list.get(i).getmDescrip();
                Log.i("closest equal",Day2Descrip);
            } else if (listtime == Day3Date) {
                Day3MinTemp = list.get(i).getmMinTemp();
                Day3MaxTemp = list.get(i).getmMaxTemp();
                Day3Descrip = list.get(i).getmDescrip();
                Log.i("closest equal","working3");
            } else if (listtime == Day4Date) {
                Day4MinTemp = list.get(i).getmMinTemp();
                Day4MaxTemp = list.get(i).getmMaxTemp();
                Day4Descrip = list.get(i).getmDescrip();
                Log.i("closest equal","working4");
            } else if (listtime == Day5Date) {
                Day5MinTemp = list.get(i).getmMinTemp();
                Day5MaxTemp = list.get(i).getmMaxTemp();
                Day5Descrip = list.get(i).getmDescrip();
                Log.i("closest equal","working5");
            }
           // else {
               // Log.i("not","not working");}
        }
        //Log.i("ddcd",Day1Descrip);
    }
    void addDataToViewModel()
    {
        viewModel.setDay1Date(Day1Date+ "");
        viewModel.setDay1Descrip(Day1Descrip+ "");
        viewModel.setDay1MaxTemp(Day1MaxTemp+"");
        viewModel.setDay1MinTemp(Day1MinTemp+"");
        viewModel.setDay2Date(Day2Date+ "");
        viewModel.setDay2Descrip(Day2Descrip+ "");
        viewModel.setDay2MaxTemp(Day2MaxTemp+"");
        viewModel.setDay2MinTemp(Day2MinTemp+"");
        viewModel.setDay3Date(Day3Date+ "");
        viewModel.setDay3Descrip(Day3Descrip+ "");
        viewModel.setDay3MaxTemp(Day3MaxTemp+"");
        viewModel.setDay3MinTemp(Day3MinTemp+"");
        viewModel.setDay4Date(Day4Date+ "");
        viewModel.setDay4Descrip(Day4Descrip+ "");
        viewModel.setDay4MaxTemp(Day4MaxTemp+"");
        viewModel.setDay4MinTemp(Day4MinTemp+"");
        viewModel.setDay5Date(Day5Date+ "");
        viewModel.setDay5Descrip(Day5Descrip+ "");
        viewModel.setDay5MaxTemp(Day5MaxTemp+"");
        viewModel.setDay5MinTemp(Day5MinTemp+"");


    }

}
