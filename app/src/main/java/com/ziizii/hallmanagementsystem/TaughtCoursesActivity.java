package com.ziizii.hallmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ziizii.hallmanagementsystem.HelperClasses.RegisteredCourse;
import com.ziizii.hallmanagementsystem.HelperClasses.RequestMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TaughtCoursesActivity extends AppCompatActivity {
    ArrayList<String> courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taught_courses);

        courses = new ArrayList<>();
        String response = getIntent().getStringExtra("response");
        try {

            JSONArray timesJSON = new JSONArray(response);

            for (int i = 0; i < timesJSON.length(); i++) {
                    JSONArray current = (JSONArray) timesJSON.get(i);
                courses.add(
                        current.getString(0)
                );

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }


        final ListView resultsListView = findViewById(R.id.resultsListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                courses
        );
        resultsListView.setAdapter(arrayAdapter);


        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String course = courses.get(i);

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        StringBuilder response = new StringBuilder();
                        JSONObject login = new JSONObject();

                        try {
                            login.put("course_num", strings[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            URL url = new URL(RequestMaker.hardcoded_ip + "getCourseStudents");
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
                        return response.toString();
                    }

                    @Override
                    protected void onPostExecute(String string)
                    {

                        Intent intent = new Intent(TaughtCoursesActivity.this, StudentListActivity.class);
                        intent.putExtra("response", string);
                        Log.i("string", string);
                        startActivity(intent);


                    }


                }.execute(course);
            }
        });




    }
}
