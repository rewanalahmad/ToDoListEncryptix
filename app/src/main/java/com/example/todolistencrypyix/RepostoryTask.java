package com.example.todolistencrypyix;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepostoryTask {
    private Dao dao;
    private LiveData<List<TaskModel>> allTasks;
    public RepostoryTask(Application application) {
        TaskDataBase database = TaskDataBase.getInstance(application);
        dao = database.Dao();
        allTasks = dao.getAlltasks();
    }
    public void insert(TaskModel model) {
        new InsertTaskAsyncTask(dao).execute(model);
    }
    public void update(TaskModel model) {
        new UpdateTaskAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(TaskModel model) {
        new DeleteTaskAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void Deletalltasks() {
        new DeleteAllTASKAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<TaskModel>> getalltasks() {
        return allTasks;
    }
    private static class InsertTaskAsyncTask extends AsyncTask<TaskModel, Void, Void> {
        private Dao dao;

        private InsertTaskAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TaskModel... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }
    private static class UpdateTaskAsyncTask extends AsyncTask<TaskModel, Void, Void> {
        private Dao dao;

        private UpdateTaskAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TaskModel... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteTaskAsyncTask extends AsyncTask<TaskModel, Void, Void> {
        private Dao dao;

        private DeleteTaskAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TaskModel... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }
    private static class DeleteAllTASKAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllTASKAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAlltasks();
            return null;
        }
    }
}
