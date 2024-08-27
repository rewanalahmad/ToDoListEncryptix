package com.example.todolistencrypyix;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titleTask;
    private String taskDescription;
    public boolean isChecked;

    //constracter
    public TaskModel(){}
    public TaskModel( String titleTask, String taskDescription,boolean isChecked) {

        this.titleTask = titleTask;
        this.taskDescription = taskDescription;
        this.isChecked=isChecked;
    }
    public TaskModel( String titleTask, String taskDescription) {

        this.titleTask = titleTask;
        this.taskDescription = taskDescription;
    }

    //methods
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }



}
