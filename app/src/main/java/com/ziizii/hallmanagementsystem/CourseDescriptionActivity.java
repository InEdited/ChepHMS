package com.ziizii.hallmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);
        try {
            JSONObject rootObject = new JSONArray(getIntent().getStringExtra("response")).getJSONObject(0);
            String course_num = getIntent().getStringExtra("course_num");
            String course_name = rootObject.getString("course_name");
            String course_desc = rootObject.getString("course_description");
            JSONArray instructors = rootObject.getJSONArray("instructor_name");

            TextView course_num_field = findViewById(R.id.course_num_view);
            TextView course_name_field = findViewById(R.id.course_name_view);
            TextView course_desc_field = findViewById(R.id.course_desc_view);
            TextView course_inst_field = findViewById(R.id.course_instructors);

            course_num_field.setText(course_num);
            course_name_field.setText(course_name);
            course_desc_field.setText(course_desc);

            course_inst_field.append(instructors.getString(0));
            for(int i = 1; i < instructors.length(); i++)
            {
                course_inst_field.append(", " + instructors.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
