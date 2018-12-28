package com.ziizii.hallmanagementsystem;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.alespero.expandablecardview.Utils;
import com.ziizii.hallmanagementsystem.HelperClasses.CourseTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CourseTimesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_times_list);
        ArrayList<CourseTime> courseTimes = new ArrayList<>();
        String response = getIntent().getStringExtra("response");
        try {

            JSONArray timesJSON = new JSONArray(response);

            for (int i = 0; i < timesJSON.length(); i++) {
                JSONArray current = (JSONArray) timesJSON.get(i);
                courseTimes.add(new CourseTime(
                        //0231
                        current.getString(0),
                        current.getString(2),
                        current.getString(3),
                        current.getInt(1)
                ));

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> listItems = new ArrayList<>();

        for(CourseTime ct : courseTimes)
        {
            listItems.add("Day:" + ct.getDay() + "\n" +
                    "Slot:" + ct.getSlot() + "\n" +
                    "Hall:" + ct.getHall() + "\n" +
                    "Instructor:" + ct.getInstructor());
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

