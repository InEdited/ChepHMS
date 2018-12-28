package com.ziizii.hallmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ziizii.hallmanagementsystem.HelperClasses.RegisteredCourse;
import com.ziizii.hallmanagementsystem.HelperClasses.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

ArrayList<Student> students = new ArrayList<>();
        String response = getIntent().getStringExtra("response");

        try {

            JSONArray timesJSON = new JSONArray(response);

            for (int i = 0; i < timesJSON.length(); i++) {
                JSONObject current = (JSONObject) timesJSON.get(i);
                students.add(new Student(
                        current.getString("student_name"),
                        current.getString("student_id").toUpperCase(),
                        current.getString("grade").toUpperCase()
                ));

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> listItems = new ArrayList<>();

        for(Student student : students)
        {
            listItems.add("" +  student.getName()+ "\n" +
                                student.getId()+ "\n" +
                    "Grade:" +  student.getGrade());
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
