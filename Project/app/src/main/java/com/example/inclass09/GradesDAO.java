/*
InClass09
GradesDAO.java
Derek Seaverson
 */
package com.example.inclass09;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GradesDAO {

    @Query("SELECT * from grades")
    List<Grades> getAll();

    @Insert
    void insertAll(Grades... grades);

    @Delete
    void delete(Grades grade);


}
