package com.ziizii.hallmanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class EmptyHallsListActivity extends AppCompatActivity {
    ArrayList<String> viewSlots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        viewSlots=  (ArrayList<String>) getIntent().getExtras().get("emptyhalls");
        final ListView resultsListView = findViewById(R.id.resultsListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                viewSlots
        );
        resultsListView.setAdapter(arrayAdapter);
     if(getIntent().getBooleanExtra("ins", false))
     {
            resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hall = viewSlots.get(i);
                String day = getIntent().getStringExtra("day");
                int slot = getIntent().getIntExtra("slot", 1);

                Intent intent = new Intent(EmptyHallsListActivity.this, ReserveSlotActivity.class);
                intent.putExtra("hall", hall);
                intent.putExtra("day", day);
                intent.putExtra("slot", slot);
                startActivity(intent);

                }
            });
        }
    }
}
