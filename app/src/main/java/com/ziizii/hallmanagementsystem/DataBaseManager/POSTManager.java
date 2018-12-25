package com.ziizii.hallmanagementsystem.DataBaseManager;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class POSTManager extends AsyncTask<Day, Slot, String>
{
    protected static String jsonify(Day day, Slot slot)
    {
        return "{" +
                    "day: " + day.getDayName() + "," +
                    "slot: " + slot.getSlotPosition() +
                "}";
    }

    @Override
    protected String doInBackground(Day day, Slot slot)
    {
        try {
            URL url = new URL("192.168.1.103:5000/getFreeHall");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            outputStreamWriter.write(jsonify(day, slot));
            Log.i("POST", "Post Successful");
            //inputStreamReader.
            outputStreamWriter.flush();
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

