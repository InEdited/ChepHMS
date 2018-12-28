package com.ziizii.hallmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ziizii.hallmanagementsystem.HelperClasses.RegisteredCourse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RegisteredCoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_courses);

        ArrayList<RegisteredCourse> courses = new ArrayList<>();
        String response = getIntent().getStringExtra("response");
        try {

            JSONArray timesJSON = new JSONArray(response);

            for (int i = 0; i < timesJSON.length(); i++) {
                JSONArray current = (JSONArray) timesJSON.get(i);
                courses.add(new RegisteredCourse(
                        current.getString(0),
                        current.getInt(1),
                        current.getString(2),
                        current.getString(3)
                ));

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> listItems = new ArrayList<>();

        for(RegisteredCourse ct : courses)
        {
            listItems.add("Course:" + ct.getCourse_num() + "\n" +
                    "Grade:" + ct.getGrade() + "\n" +
                    "Year:" + ct.getYear() + "\n" +
                    "Semester:" + ct.getSemester());
        }


        final ListView resultsListView = findViewById(R.id.resultsListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listItems
        );
        resultsListView.setAdapter(arrayAdapter);



    }
}
