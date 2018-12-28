package com.ziizii.hallmanagementsystem.HelperClasses;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ziizii.hallmanagementsystem.LoginActivity;
import com.ziizii.hallmanagementsystem.StudentMenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RequestMaker {

    private static String username = "";

    public static String hardcoded_ip = "http://192.168.1.10:5000/";
    public static void requestEmptyHalls(String day, int slot)
    {
        new AsyncTask<Day_Slot, Void, Void>() {
            @Override
            protected Void doInBackground(Day_Slot... day_slots) {
                return null;
            }
        }.execute(new Day_Slot(day, slot));
    }


    public static ArrayList<String> jsondear(JSONArray arr) throws JSONException {
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < arr.length(); i++)
        {
            result.add(((JSONArray)arr.get(i)).getString(0));
        }

        return result;
    }


    public static void requestLogin(final String username, String password)
    {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                StringBuilder response = new StringBuilder();
                JSONObject login = new JSONObject();

                try {
                    login.put("username", strings[0]);
                    login.put("password", strings[1]);
                    RequestMaker.username = strings[0];
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    URL url = new URL(hardcoded_ip + "login");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type",
                            "application/json");

                    connection.setRequestProperty("Content-Language", "en-US");

                    connection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream (
                            connection.getOutputStream());
                    wr.writeBytes(login.toString());
                    wr.close();

                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));


                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    connection.disconnect();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    return ((JSONArray)new JSONArray(response.toString()).get(0)).getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String string)
            {

                if (string.equals("instructor"))
                {
                    LoginActivity.INSTRUCTOR = true;
                }


                LoginActivity.FINISHED = true;

            }


        }.execute(username, password);
    }
}
