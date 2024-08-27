package com.example.todolistencrypyix;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recycle;
    private AdapterTask adapter;
    private CheckBox checkBox;
    private Toolbar toolbar;
    private static final int ADD_TASK_REQUEST = 1;
    private static final int EDIT_TASK_REQUEST=2;
    private ViewModel viewModel;
    private TextView textView;
    private TaskDataBase taskDataBase;
    private List<TaskModel> taskModel;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


viewModel=new ViewModelProvider(this).get(ViewModel.class);

        // initializing our variable for our recycler view and fab.
        FloatingActionButton ftn=findViewById(R.id.idFABAdd);
        recycle = findViewById(R.id.recycle);
        checkBox=findViewById(R.id.checkBox);
        textView=findViewById(R.id.textView);


        //Anamiation

        YoYo.with(Techniques.BounceInRight)
                .duration(700)
                .repeat(7)
                .playOn(findViewById(R.id.textView));





        /*
        delete=findViewById(R.id.deletebtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = null;
                viewModel.delete(adapter.getTASKAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "task deleted", Toast.LENGTH_SHORT).show();
            }
        });*/


        ftn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting a new activity for adding a new course
                // and passing a constant value in it.
                Intent intent = new Intent(MainActivity.this,
                        AddActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });



        taskDataBase= Room.databaseBuilder(this,TaskDataBase.class,"my database").build();
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);
        adapter=new AdapterTask(viewModel);
        recycle.setAdapter(adapter);
       
        

        
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAlltasks().observe(this, new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> models) {
                adapter.submitList(models);
                adapter.settasks(models);
            }
        });


        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper
                .LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewModel.delete(adapter.getTASKAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "task deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {
                // Your custom drawing code here
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .addActionIcon(R.drawable.baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recycle);

                // below line is use to attach this to recycler view.





        adapter.setOnItemClickListener(new AdapterTask.OnItemClickListener() {
            @Override
            public void onItemClick(TaskModel model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_ID,model.getId());
                intent.putExtra(AddActivity.EXTRA_TITLE, model.getTitleTask());
                intent.putExtra(AddActivity.EXTRA_DESCRIPTION,model.getTaskDescription());
                // below line is to start a new activity and
                // adding a edit course constant.
                startActivityForResult(intent, EDIT_TASK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String titletask = data.getStringExtra(AddActivity.EXTRA_TITLE);
            String desctask = data.getStringExtra(AddActivity.EXTRA_DESCRIPTION);
            TaskModel model = new TaskModel(titletask, desctask);
            viewModel.insert(model);
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Task can't be updated",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String titletask = data.getStringExtra(AddActivity.EXTRA_TITLE);
            String desctask = data.getStringExtra(AddActivity.EXTRA_DESCRIPTION);
            TaskModel model = new TaskModel(titletask, desctask);
            model.setId(id);
            viewModel.update(model);
            Toast.makeText(this, "task updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "task not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void ShowTost(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}




