package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;


public class Day1Fragment extends Fragment {
    View view;
    TextView wind, pressure, humidity, fellTemp, original_temp, minMaxTemp, description;

    private MyViewModel viewModel;

    public Day1Fragment() {
    }

    public void assignText() {
        wind = view.findViewById(R.id.wind_speed);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        fellTemp = view.findViewById(R.id.fell_temp);
        original_temp = view.findViewById(R.id.original_temp);
        minMaxTemp = view.findViewById(R.id.min_max_temp);
        description = view.findViewById(R.id.description);
        String originalTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmOriginalTemp());
        String feelTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmFeelTemp());
        String maxTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmMaxTemp());
        String minTempFormatted = String.format("%.0f", viewModel.getList().get(0).getmMinTemp());
        wind.setText(viewModel.getList().get(0).getmWindSpeed() + "km/h");
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

