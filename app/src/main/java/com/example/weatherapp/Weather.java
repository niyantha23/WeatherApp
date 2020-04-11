package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;


public class Weather {

    private int mTimeStamp;
    private String mMinTemp;
    private String mMaxTemp;
    private String mDescrip;

    public Weather(int timeStamp, String descrip,String minTemp,String maxTemp){
        mTimeStamp=timeStamp;
        mMinTemp=minTemp;
        mMaxTemp=maxTemp;
        mDescrip=descrip;
    }

    public int getmTimeStamp() {
        return mTimeStamp;
    }

    public String getmMinTemp() {
        return mMinTemp;
    }

    public String getmMaxTemp() {
        return mMaxTemp;
    }

    public String getmDescrip() {
        return mDescrip;
    }
}
