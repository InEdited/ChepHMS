package com.ziizii.hallmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GpaViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_view);

        double gpa = getIntent().getDoubleExtra("GPA", 0);

        TextView gpa_view = findViewById(R.id.gpa_view);
        gpa_view.setText("Your GPA is\n" + gpa);
    }
}
