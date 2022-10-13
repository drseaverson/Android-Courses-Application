/*
InClass09
ClassAdapter.java
Derek Seaverson
 */
package com.example.inclass09;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<Grades> {

    AppDatabase db;

    public ClassAdapter(@NonNull Context context, int resource, @NonNull List<Grades> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_view_layout, parent, false);
        }
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "grades.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        //assign all information based on position
        Grades grade = getItem(position);

        TextView textViewCourseNum = convertView.findViewById(R.id.textViewCourseNum);
        TextView textViewCourseName = convertView.findViewById(R.id.textViewCourseName);
        TextView textViewCourseHours = convertView.findViewById(R.id.textViewCourseHours);
        TextView textViewCourseGrade = convertView.findViewById(R.id.textViewCourseGrade);

        textViewCourseGrade.setText(grade.letterGrade);
        textViewCourseNum.setText(grade.courseNum);
        textViewCourseName.setText(grade.courseName);
        textViewCourseHours.setText(grade.creditHours + " Credit Hours");

        ImageButton imageButtonTrash = convertView.findViewById(R.id.imageButtonTrash);
        imageButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete the course?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.gradesDAO().delete(grade);
                        mOnDataChangedListener.onDataChanged(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return convertView;
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
    }

    OnDataChangedListener mOnDataChangedListener;

    public interface OnDataChangedListener {
        void onDataChanged(int position);
    }


}
