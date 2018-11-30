package com.pokidin.a.quakewitness;

public class Earthquake {

    private double mMag;
    private String mCity;
    private long mTimeInMilliseconds;
    private String mUrl;

    public double getMag() {
        return mMag;
    }

    String getCity() {
        return mCity;
    }

    long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    String getUrl() {
        return mUrl;
    }

    Earthquake(double mag, String city, long timeInMilliseconds, String url) {

        mMag = mag;
        mCity = city;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }
}
