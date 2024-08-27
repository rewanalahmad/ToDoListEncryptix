package com.example.todolistencrypyix;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(TaskModel model);

    @Update
    void update(TaskModel model);

    @Delete
    void delete(TaskModel model);

    @Query("DELETE FROM task_table")
    void deleteAlltasks();

    @Query("SELECT * FROM task_table ORDER BY titleTask ASC")
    LiveData<List<TaskModel>> getAlltasks();

    @Query("SELECT * FROM task_table")
    List<TaskModel> getall();

}
