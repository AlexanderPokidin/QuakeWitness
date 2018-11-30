package com.pokidin.a.quakewitness;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    // Make an HTTP request to the given URL and return a String as the response.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Error with creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response method :" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

//    Create a private constructor because no one should ever create a QueryUtils object.
//    This class is only meant to hold static variables and methods, which can be accessed
//    directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
    private QueryUtils() {
    }

    // Return a list of Earthquake objects that has been built up from parsing a JSON response.
    private static ArrayList<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // Extract the JSONArray associated with the key called "features"
            JSONArray quakeArray = baseJsonResponse.getJSONArray("features");

            // For each earthquake in the earthquakeArray, create an Earthquake object
            if (quakeArray.length() > 0) {
                for (int i = 0; i < quakeArray.length(); i++) {

                    // Get a single earthquake at position i within the list of earthquakes
                    JSONObject currentEarthquake = quakeArray.getJSONObject(i);

                    // For a given earthquake, extract the JSONObject associated with the
                    // key called "properties"
                    JSONObject quakeProperties = currentEarthquake.getJSONObject("properties");
                    double mag = quakeProperties.getDouble("mag");
                    String city = quakeProperties.getString("place");
                    long time = quakeProperties.getLong("time");

                    // Extract the value for the key called "url"
                    String url = quakeProperties.getString("url");

                    // Create a new Earthquake baseJsonResponse with the magnitude, location, time,
                    // and url from the JSON response.
                    Earthquake earthquake = new Earthquake(mag, city, time, url);
                    earthquakes.add(earthquake);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    // Query the USGS dataset and return a list of Earthquake objects.
    static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
        }

        Log.d(TAG, "fetchEarthquakeData checked");

        // Extract relevant fields from the JSON response and create a list of Earthquakes
        return extractFeatureFromJson(jsonResponse);
    }
}
