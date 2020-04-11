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
    TextView date;
    String date1;
    private MyViewModel viewModel;



    public Day1Fragment() {
    }
     public void  assignText(){
         date=view.findViewById(R.id.day1_date);
          date.setText(viewModel.getDay1Descrip());

           }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.day1_fragment,container,false);

        Log.i("whaat2",viewModel.getDay1Date()+"");


        return view;



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MyViewModel.class);

        Log.i("whaat1",viewModel.getDay1Date()+"");




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("whaat0",viewModel.getDay1Date()+"");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        String content =viewModel.getDay1Date()+viewModel.getDay1Descrip()+viewModel.getDay1MaxTemp()+viewModel.getDay2Descrip();
//

      Log.i("whaat",viewModel.getDay1Date()+"");
    }
}
