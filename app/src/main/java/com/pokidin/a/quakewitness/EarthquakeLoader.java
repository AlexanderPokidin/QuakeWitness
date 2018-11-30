package com.pokidin.a.quakewitness;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

//Loads a list of earthquakes by using an AsyncTask to perform the network request to the given URL.
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String TAG = EarthquakeLoader.class.getSimpleName();

    // Query URL
    private String mUrl;

    EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading checked");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.d(TAG, "loadInBackground checked");

        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        return QueryUtils.fetchEarthquakeData(mUrl);
    }
}