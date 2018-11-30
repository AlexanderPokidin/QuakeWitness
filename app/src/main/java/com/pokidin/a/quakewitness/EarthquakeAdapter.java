package com.pokidin.a.quakewitness;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public static final String TAG = "EarthquakeAdapter";

    private static final String LOCATION_SEPARATOR = " of ";

    EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);

        TextView magTextView = listItemView.findViewById(R.id.mag);
        magTextView.setText(formatMagnitude(currentEarthquake.getMag()));

        // Set the proper background color on the magnitude circle.
        GradientDrawable magCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magColor = getMagColor(currentEarthquake.getMag());

        // Set the color on the magnitude circle
        magCircle.setColor(magColor);

        TextView positionTextView = listItemView.findViewById(R.id.details);
        positionTextView.setText(formatLocation(currentEarthquake.getCity())[0]);

        TextView cityTextView = listItemView.findViewById(R.id.location);
        cityTextView.setText(formatLocation(currentEarthquake.getCity())[1]);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        TextView dateTextView = listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    private int getMagColor(double magnitude) {
        int magColorId;
        int mag = (int) magnitude;
        switch (mag) {
            case 0:
            case 1:
                magColorId = R.color.mag1;
                break;
            case 2:
                magColorId = R.color.mag2;
                break;
            case 3:
                magColorId = R.color.mag3;
                break;
            case 4:
                magColorId = R.color.mag4;
                break;
            case 5:
                magColorId = R.color.mag5;
                break;
            case 6:
                magColorId = R.color.mag6;
                break;
            case 7:
                magColorId = R.color.mag7;
                break;
            case 8:
                magColorId = R.color.mag8;
                break;
            case 9:
                magColorId = R.color.mag9;
                break;
            default:
                magColorId = R.color.mag10;
                break;
        }
        return ContextCompat.getColor(getContext(), magColorId);
    }

    // Format date display
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(dateObject);
    }

    // Format time display
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    // Format location display  in two-line
    private String[] formatLocation(String location) {
        String[] strings;
        if (location.contains(LOCATION_SEPARATOR)) {
            strings = location.split(LOCATION_SEPARATOR);
            strings[0] = strings[0] + LOCATION_SEPARATOR;
        } else {
            strings = new String[]{getContext().getString(R.string.near_the), location};
        }
        return strings;
    }

    // Format magnitude display
    private String formatMagnitude(double mag) {
        DecimalFormat inputFormat = new DecimalFormat("0.0");
        return inputFormat.format(mag);
    }
}