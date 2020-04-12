package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;


public class Weather {

    private int mTimeStamp;
    private float mMinTemp;
    private float mMaxTemp;
    private String mDescrip;
    private  float mOriginalTemp;
    private  float mFeelTemp;
    private  float mPressure;
    private float mHumidity;
    private  float mWindSpeed;

    public Weather(int mTimeStamp, float mMinTemp, float mMaxTemp, String mDescrip, float mOriginalTemp, float mFeelTemp, float mPressure, float mHumidity, float mWindSpeed) {
        this.mTimeStamp = mTimeStamp;
        this.mMinTemp = mMinTemp;
        this.mMaxTemp = mMaxTemp;
        this.mDescrip = mDescrip;
        this.mOriginalTemp = mOriginalTemp;
        this.mFeelTemp = mFeelTemp;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
        this.mWindSpeed = mWindSpeed;
    }

    public int getmTimeStamp() {
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

    public float getmOriginalTemp() {
        return mOriginalTemp;
    }

    public float getmFeelTemp() {
        return mFeelTemp;
    }

    public float getmPressure() {
        return mPressure;
    }

    public float getmHumidity() {
        return mHumidity;
    }

    public float getmWindSpeed() {
        return mWindSpeed;
    }
}

