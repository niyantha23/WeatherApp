package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class Day5Fragment extends Fragment {
    View view;
    private MyViewModel viewModel;
    TextView wind, pressure, humidity, fellTemp, original_temp, minMaxTemp, description,details;
    CardView card;

    public Day5Fragment() {
    }
    public void setcolor(){
        MainActivity mainActivity= (MainActivity) getActivity();
        RelativeLayout base=view.findViewById(R.id.baselayout);
        String des=viewModel.getList().get(4).getmDescrip();
        Log.i("des",des);
        if(des.equals("clear sky")){
            mainActivity.rootLayout.setBackgroundResource(R.drawable.clearsky);
        }
        else if(des.equals("few clouds")||des.equals("scattered clouds")||des.equals("broken clouds")||des.equals("moderate clouds")||des.equals("overcast clouds")){
            mainActivity.rootLayout.setBackgroundResource(R.drawable.clouds);
        }
        else if(des.equals("light rain")||des.equals("heavy rain")||des.equals("moderate rain")){
            mainActivity.rootLayout.setBackgroundResource(R.drawable.rain4);
      }
        else  if(des.equals("sunny sky")){
            mainActivity.rootLayout.setBackgroundResource(R.drawable.sunny3);
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
        String originalTempFormatted = String.format("%.0f", viewModel.getList().get(4).getmOriginalTemp());
        String feelTempFormatted = String.format("%.0f", viewModel.getList().get(4).getmFeelTemp());
        String maxTempFormatted = String.format("%.1f", viewModel.getList().get(4).getmMaxTemp());
        String minTempFormatted = String.format("%.1f", viewModel.getList().get(4).getmMinTemp());
        String windSpeedFormatted=String.format("%.1f",viewModel.getList().get(4).getmWindSpeed());
        wind.setText(windSpeedFormatted + "km/h");
        pressure.setText(viewModel.getList().get(4).getmPressure() + "hPa");
        humidity.setText(viewModel.getList().get(4).getmHumidity() + "%");
        fellTemp.setText(feelTempFormatted + "\u2103\n"+"(Feels Like)");
        original_temp.setText(originalTempFormatted + "\u2103");
        minMaxTemp.setText(minTempFormatted + "\u2103/" + maxTempFormatted + "\u2103");
        String des=viewModel.getList().get(4).getmDescrip();
        String desFormatted=des.substring(0,1).toUpperCase()+des.substring(1).toLowerCase();
        description.setText(desFormatted);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MyViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.day5_fragment,container,false);
        return view;
    }
}
