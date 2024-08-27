package com.example.todolistencrypyix;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.function.ToDoubleBiFunction;

public class AddActivity extends AppCompatActivity {
    EditText title,desc;
    Button savebtn;
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.gtappdevelopers.gfgroomdatabase.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_DESCRIPTION";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        title=findViewById(R.id.editTextText4);
        desc=findViewById(R.id.descTextText2);
        savebtn=findViewById(R.id.savebtn);




        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.REVERSE, 0.5f, Animation.ZORDER_NORMAL, 0.5f);
        animation.setDuration(500);
        savebtn.startAnimation(animation);






        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            desc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tasktitle =title.getText().toString();
                String taskDesc = desc.getText().toString();
                if (tasktitle.isEmpty()) {
                    Toast.makeText(AddActivity.this,
                            "Please enter the valid task details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                savetask(tasktitle, taskDesc);

            }
        });

    }

    private void savetask(String tasktitle, String taskDesc) {
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,tasktitle);
        data.putExtra(EXTRA_DESCRIPTION, taskDesc);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        Toast.makeText(this, "task has been saved . ", Toast.LENGTH_SHORT).show();
    }
}

