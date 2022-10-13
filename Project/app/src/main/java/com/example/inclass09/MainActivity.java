/*
InClass09
MainActivity.java
Derek Seaverson
 */
package com.example.inclass09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String TAG = "demo";
    AppDatabase db;
    ListView listViewCourses;
    TextView textViewGPA, textViewTotalHours;
    public static List<Grades> grades;
    ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Grades");

        db = Room.databaseBuilder(this, AppDatabase.class, "grades.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        textViewGPA = findViewById(R.id.textViewGPA);
        textViewTotalHours = findViewById(R.id.textViewTotalHours);
        grades = db.gradesDAO().getAll();
        calcGPA(grades);
        listViewCourses = findViewById(R.id.listViewCourses);
        adapter = new ClassAdapter(this, R.layout.class_view_layout, grades);
        listViewCourses.setAdapter(adapter);
        adapter.setOnDataChangedListener(new ClassAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged(int position) {
                grades.remove(position);
                calcGPA(grades);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_classes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this, AddClass.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void calcGPA(List<Grades> grades) {
        DecimalFormat df = new DecimalFormat("###.##");
        double gpa = 0.0;
        double creditHours = 0.0;
        for (Grades grade : grades) {
            creditHours += grade.creditHours;
            switch (grade.letterGrade) {

                case "A":
                    gpa += (4 * grade.creditHours);
                    break;

                case "B":
                    gpa += (3 * grade.creditHours);
                    break;

                case "C":
                    gpa += (2 * grade.creditHours);
                    break;

                case "D":
                    gpa += grade.creditHours;
                    break;

                default:
                    //adds zero (does nothing)
                    break;
            }
        }
        if ((gpa > 0)) {
            gpa = gpa / creditHours;
        }
        textViewTotalHours.setText("Total Hours: " + creditHours);
        textViewGPA.setText("GPA: " + df.format(gpa));
    }
}