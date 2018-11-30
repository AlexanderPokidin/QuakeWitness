package com.pokidin.a.quakewitness;

public class Earthquake {

    private double mMag;
    private String mCity;
    private long mTimeInMilliseconds;
    private String mUrl;

    public double getMag() {
        return mMag;
    }

    public String getCity() {
        return mCity;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }

    public Earthquake(double mag, String city, long timeInMilliseconds, String url) {

        mMag = mag;
        mCity = city;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }
}
