package com.example.weatherapp;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class MyViewModel extends ViewModel {
    private List<Weather> list;
    public List<Weather> getList() { return list; }
    public void setList(List<Weather> list) { this.list = list; }
}


