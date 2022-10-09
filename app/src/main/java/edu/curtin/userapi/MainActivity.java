package edu.curtin.userapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.curtin.userapi.api.TaskHandler;

public class MainActivity extends AppCompatActivity {

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBarId);
        progressBar.setVisibility(View.INVISIBLE);

        TaskHandler taskHandler = new TaskHandler(MainActivity.this, this, progressBar);
        executorService.execute(taskHandler);
    }
}