package com.ziizii.hallmanagementsystem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ReserveSlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_slot);

        final EditText course_num_field = findViewById(R.id.course_num);
        final EditText instructor_field = findViewById(R.id.instructor);

        Button reserve_btn = findViewById(R.id.reserve_btn);

        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course_num = course_num_field.getText().toString().toUpperCase();
                String instructor = instructor_field.getText().toString();
                String hall = getIntent().getStringExtra("hall");
                String day = getIntent().getStringExtra("day");
                int slot = getIntent().getIntExtra("slot", 1);

                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... strings) {
                        StringBuilder response = new StringBuilder();
                        JSONObject login = new JSONObject();

                        try {
                            login.put("day", strings[0]);
                            login.put("slot", Integer.parseInt(strings[1]));
                            login.put("hall", strings[2]);
                            login.put("course_num", strings[3]);
                            login.put("instructor_name", strings[4]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            URL url = new URL(RequestMaker.hardcoded_ip + "reserveSlot");
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


                            return null;

                    }
                }.execute(day, String.valueOf(slot), hall, course_num, instructor);
            }
        });


    }
}
