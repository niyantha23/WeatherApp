package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;


public class Weather {

    @SerializedName("dt")
    private long mTimeStamp;
    @SerializedName("temp_min")
    private float mMinTemp;
    @SerializedName("temp_max")
    private float mMaxTemp;
    @SerializedName("description")
    private String mDescrip;

    public Weather(long timeStamp,float minTemp,float maxTemp, String descrip){
        mTimeStamp=timeStamp;
        mMinTemp=minTemp;
        mMaxTemp=maxTemp;
        mDescrip=descrip;
    }

    public long getmTimeStamp() {
        return mTimeStamp;
    }

    public float getmMinTemp() {
        return mMinTemp;
    }

    public float getmMaxTemp() {
        return mMaxTemp;
    }

    public String getmDescrip() {
        return mDescrip;
    }
}
