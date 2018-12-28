package com.ziizii.hallmanagementsystem;

import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ziizii.hallmanagementsystem.HelperClasses.Day_Slot;
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
import java.util.Arrays;
import java.util.List;

public class EmptyHallsActivity extends AppCompatActivity{

    private Spinner daySpinner;
    private Spinner slotSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button NOW = findViewById(R.id.NOW);

        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        ArrayAdapter<String> day_adapter;
        List<String> day_list;

        day_list = Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday");

        day_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, day_list);

        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(day_adapter);


        slotSpinner = (Spinner) findViewById(R.id.slot_spinner);
        ArrayAdapter<String> slot_adapter;
        List<String> slot_list;

        slot_list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

        slot_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, slot_list);

        slot_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slotSpinner.setAdapter(slot_adapter);

        NOW.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                daySpinner.setSelection(day % 6, true);
            }

        });

        FloatingActionButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ArrayList <Hall> emptySlots = new ArrayList<>();
                ArrayList<String> emptyHalls = new ArrayList<>();
                final String day = (String) daySpinner.getSelectedItem();
                final int slotNo = Integer.parseInt((String) slotSpinner.getSelectedItem());

                new AsyncTask<Day_Slot, Void, ArrayList<String>>() {
                    @Override
                    protected ArrayList<String> doInBackground(Day_Slot... daySlots) {
                        StringBuilder response = new StringBuilder();
                        JSONObject login = new JSONObject();

                        try {
                            login.put("day", daySlots[0].getDay());
                            login.put("slot", daySlots[0].getSlot());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            URL url = new URL(RequestMaker.hardcoded_ip + "getFreeHall");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("Content-Type",
                                    "application/json");

                            connection.setRequestProperty("Content-Language", "en-US");

                            connection.setDoOutput(true);
                            DataOutputStream wr = new DataOutputStream(
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
                            return RequestMaker.jsondear(new JSONArray(response.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(ArrayList<String> halls) {


                        Intent intent = new Intent(EmptyHallsActivity.this, EmptyHallsListActivity.class);
                        intent.putExtra("emptyhalls", halls);
                        intent.putExtra("day", day);
                        intent.putExtra("slot", slotNo);
                        intent.putExtra("ins", getIntent().getBooleanExtra("ins", false));
                        startActivity(intent);


                    }


                }.execute(new Day_Slot(day, slotNo));

            }
        });

    }
}
