package com.example.weatherapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;


public class Day1Fragment extends Fragment {
    View view;
    TextView wind, pressure, humidity, fellTemp, original_temp, minMaxTemp, description,details;
    CardView card;

    private MyViewModel viewModel;

    public Day1Fragment() {
    }
    public void setcolor(){
        RelativeLayout base=view.findViewById(R.id.baselayout);
        String des=viewModel.getList().get(0).getmDescrip();
                   Log.i("des",des);
               if(des.equals("clear sky")){
                   base.setBackgroundResource(R.drawable.clearsky);
               }
               else if(des.equals("few clouds")||des.equals("scattered clouds")||des.equals("broken clouds")||des.equals("moderate clouds")||des.equals("overcast clouds")){
                   base.setBackgroundResource(R.drawable.clouds);
               }
               else if(des.equals("light rain")||des.equals("heavy rain")||des.equals("moderate rain")){
                   base.setBackgroundResource(R.drawable.rain2);
                   wind.setTextColor(ContextCompat.getColor(getContext(),R.color.Rainyskytextcolor));
                   pressure.setTextColor(ContextCompat.getColor(getContext(),R.color.Rainyskytextcolor));
                   humidity.setTextColor(ContextCompat.getColor(getContext(),R.color.Rainyskytextcolor));
                   fellTemp.setTextColor(ContextCompat.getColor(getContext(),R.color.Rainyskytextcolor));
                   details.setTextColor(ContextCompat.getColor(getContext(),R.color.Rainyskytextcolor));
                   wind.setBackgroundResource(R.drawable.background);
                   pressure.setBackgroundResource(R.drawable.background);
                   humidity.setBackgroundResource(R.drawable.background);
                   fellTemp.setBackgroundResource(R.drawable.background);
                   card.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.SunnyskCardBackground));
                                                }
               else  if(des.equals("sunny sky")){
                   base.setBackgroundResource(R.drawable.sunny3);
                   wind.setTextColor(ContextCompat.getColor(getContext(),R.color.sunnyskytextcolor));
                   pressure.setTextColor(ContextCompat.getColor(getContext(),R.color.sunnyskytextcolor));
                   humidity.setTextColor(ContextCompat.getColor(getContext(),R.color.sunnyskytextcolor));
                   fellTemp.setTextColor(ContextCompat.getColor(getContext(),R.color.sunnyskytextcolor));
                   details.setTextColor(ContextCompat.getColor(getContext(),R.color.sunnyskytextcolor));
                   wind.setBackgroundResource(R.drawable.background);
                   pressure.setBackgroundResource(R.drawable.background);
                   humidity.setBackgroundResource(R.drawable.background);
                   fellTemp.setBackgroundResource(R.drawable.background);
                   card.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.SunnyskCardBackground));

               }
           }

    public void assignText() {

        wind = view.findViewById(R.id.wind_speed);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        fellTemp = view.findViewById(R.id.fell_temp);
        original_temp = view.findViewById(R.id.original_temp);
        minMaxTemp = view.findViewById(R.id.min_max_temp);
        description = view.findViewById(R.id.description);
        details=view.findViewById(R.id.details);
        card=view.findViewById(R.id.card_view);
        setcolor();
        String originalTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmOriginalTemp());
        String feelTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmFeelTemp());
        String maxTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmMaxTemp());
        String minTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmMinTemp());
        String windSpeedFormatted=String.format("%.1f",viewModel.getList().get(0).getmWindSpeed());
        wind.setText(windSpeedFormatted + "km/h");
        pressure.setText(viewModel.getList().get(0).getmPressure() + "hPa");
        humidity.setText(viewModel.getList().get(0).getmHumidity() + "%");
        fellTemp.setText(feelTempFormatted + "\u2103");
        original_temp.setText(originalTempFormatted + "\u2103");
        minMaxTemp.setText(minTempFormatted + "\u2103\n\t\t/\n" + maxTempFormatted + "\u2103");
        description.setText(viewModel.getList().get(0).getmDescrip());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.day1_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MyViewModel.class);
    }
}

