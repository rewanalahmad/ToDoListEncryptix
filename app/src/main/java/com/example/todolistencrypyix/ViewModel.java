package com.example.todolistencrypyix;


import android.app.Application;
import android.app.appsearch.PackageIdentifier;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private RepostoryTask repository;
    private LiveData<List<TaskModel>> alltasks;
    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new RepostoryTask(application);
        alltasks= repository.getalltasks();
    }
    public void insert(TaskModel model) {
        repository.insert(model);
    }
    public void update(TaskModel model) {
        repository.update(model);
    }
    public void delete(TaskModel model) {
        repository.delete(model);
    }
    public void deleteAllTasks() {
        repository.Deletalltasks();
    }
    public LiveData<List<TaskModel>> getAlltasks() {
        return alltasks;
    }

}