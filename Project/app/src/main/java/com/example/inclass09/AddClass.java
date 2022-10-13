/*
InClass09
AddClass.java
Derek Seaverson
 */
package com.example.inclass09;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.view.View;
import android.widget.*;

public class AddClass extends AppCompatActivity {

    String letterGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "grades.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        EditText editTextCourseNum = findViewById(R.id.editTextCourseNum);
        EditText editTextCourseName = findViewById(R.id.editTextCourseName);
        EditText editTextCreditHours = findViewById(R.id.editTextCreditHours);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNum = editTextCourseNum.getText().toString();
                String courseName = editTextCourseName.getText().toString();
                String credit = editTextCreditHours.getText().toString();
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddClass.this, "Please select a course grade", Toast.LENGTH_SHORT).show();
                } else if (courseNum.equals("")) {
                    Toast.makeText(AddClass.this, "Please enter a course number", Toast.LENGTH_SHORT).show();
                } else if (courseName.equals("")) {
                    Toast.makeText(AddClass.this, "Please enter a course name", Toast.LENGTH_SHORT).show();
                } else if (credit.equals("")) {
                    Toast.makeText(AddClass.this, "Please enter the course credit hours", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(credit) < 1 || Integer.parseInt(credit) > 4) {
                    Toast.makeText(AddClass.this, "Please enter valid credit hours from 1 - 4", Toast.LENGTH_SHORT).show();
                } else {
                    int id = radioGroup.getCheckedRadioButtonId();
                    if (id == R.id.radioButtonA) {
                        letterGrade = "A";
                    } else if (id == R.id.radioButtonB) {
                        letterGrade = "B";
                    } else if (id == R.id.radioButtonC) {
                        letterGrade = "C";
                    } else if (id == R.id.radioButtonD) {
                        letterGrade = "D";
                    } else {
                        letterGrade = "F";
                    }
                    int creditHours = Integer.parseInt(editTextCreditHours.getText().toString());
                    db.gradesDAO().insertAll(new Grades(letterGrade, courseNum, courseName, creditHours));
                    startActivity(new Intent(AddClass.this, MainActivity.class));
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddClass.this, MainActivity.class));
            }
        });

    }

}
