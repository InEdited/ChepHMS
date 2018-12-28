package com.ziizii.hallmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.ziizii.hallmanagementsystem.HelperClasses.RequestMaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InstructorMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);

        CardView empty_hall_search = findViewById(R.id.empty_halls_item);
        empty_hall_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuActivity.this, EmptyHallsActivity.class);
                intent.putExtra("ins", getIntent().getBooleanExtra("ins", false));
                startActivity(intent);
            }
        });

        CardView course_times = findViewById(R.id.course_times_item);
        course_times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuActivity.this, CourseFieldActivity.class);
                intent.putExtra("request_type", "time");
                startActivity(intent);
            }
        });

        CardView course_desc = findViewById(R.id.course_detail_item);
        course_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuActivity.this, CourseFieldActivity.class);
                intent.putExtra("request_type", "desc");
                startActivity(intent);
            }
        });

        CardView students_item = findViewById(R.id.students_item);
        students_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        StringBuilder response = new StringBuilder();
                        JSONObject login = new JSONObject();

                        try {
                            login.put("username", strings[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            URL url = new URL(RequestMaker.hardcoded_ip + "getTaughtCourses");
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

                        Intent intent = new Intent(InstructorMenuActivity.this, TaughtCoursesActivity.class);
                        intent.putExtra("response", string);
                        startActivity(intent);


                    }


                }.execute(getIntent().getStringExtra("username"));
            }
        });


    }
}
