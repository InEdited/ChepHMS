package com.ziizii.hallmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ziizii.hallmanagementsystem.HelperClasses.CourseTime;
import com.ziizii.hallmanagementsystem.HelperClasses.RequestMaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CourseFieldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_times);
        final EditText course_num = findViewById(R.id.course_num);
        Button search_btn = findViewById(R.id.search_btn);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String courseNum = course_num.getText().toString().toUpperCase();
            if(getIntent().getStringExtra("request_type").equals("time"))
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        StringBuilder response = new StringBuilder();
                        JSONObject login = new JSONObject();
                        ArrayList<CourseTime> courseTimes = new ArrayList<>();
                        try {
                            login.put("course_num", strings[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            URL url = new URL(RequestMaker.hardcoded_ip + "getCourseTime");
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
                            Log.i("Reserve", "failed successfully");
                            e.printStackTrace();
                        }

                        return response.toString();

                    }

                    @Override
                    protected void onPostExecute(String response) {



                        Intent intent  = new Intent(CourseFieldActivity.this, CourseTimesListActivity.class);
                        intent.putExtra("response", response);

                        startActivity(intent);
                    }
                }.execute(courseNum);
            else
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        StringBuilder response = new StringBuilder();
                        JSONObject login = new JSONObject();
                        ArrayList<CourseTime> courseTimes = new ArrayList<>();
                        try {
                            login.put("course_num", strings[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            URL url = new URL(RequestMaker.hardcoded_ip + "getCourseData");
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
                            Log.i("Reserve", "failed successfully");
                            e.printStackTrace();
                        }

                        return response.toString();

                    }

                    @Override
                    protected void onPostExecute(String response) {



                        Intent intent  = new Intent(CourseFieldActivity.this, CourseDescriptionActivity.class);
                        intent.putExtra("response", response);
                        intent.putExtra("course_num", courseNum);

                        startActivity(intent);
                    }
                }.execute(courseNum);















            }
        });

    }



}
