package com.ziizii.hallmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ziizii.hallmanagementsystem.HelperClasses.RequestMaker;

public class IPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        final EditText ip_text = findViewById(R.id.ip_text_view);
        Button start_button = findViewById(R.id.start_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestMaker.hardcoded_ip = "http://" + ip_text.getText().toString() + ":5000/";
                Intent intent = new Intent(IPActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
