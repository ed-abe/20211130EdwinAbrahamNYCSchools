package com.edwin.a20211130_edwinabraham_nycschools.database;

import com.edwin.a20211130_edwinabraham_nycschools.pojo.School;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<School> school);

    @Query("DELETE FROM school_table")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM school_table ORDER BY school_name ASC")
    LiveData<List<School>> getSchools();

    @Transaction
    @Query("SELECT * FROM school_table where school_name like :searchString ORDER BY school_name ASC")
    LiveData<List<School>> getSchoolsFiltered(String searchString);
}
