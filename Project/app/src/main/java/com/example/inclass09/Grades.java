/*
InClass09
Grades.java
Derek Seaverson
 */
package com.example.inclass09;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grades")
public class Grades {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String letterGrade;

    @ColumnInfo
    public String courseNum;

    @ColumnInfo
    public String courseName;

    @ColumnInfo
    public int creditHours;

    public Grades(String letterGrade, String courseNum, String courseName, int creditHours) {
        this.letterGrade = letterGrade;
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }

    public Grades() {

    }

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", letterGrade='" + letterGrade + '\'' +
                ", courseNum='" + courseNum + '\'' +
                ", courseName='" + courseName + '\'' +
                ", creditHours=" + creditHours +
                '}';
    }
}
