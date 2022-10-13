/*
InClass09
AppDatabase.java
Derek Seaverson
 */
package com.example.inclass09;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Grades.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GradesDAO gradesDAO();
}

