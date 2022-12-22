package com.example.experiment4;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView time;
    Button startPause;
    Button activity;
    Button reset;

    long startTime = 0;
    long milliseconds;
    int seconds;
    int minutes;
    int hours;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            milliseconds = System.currentTimeMillis() - startTime;
            seconds = (int) (milliseconds / 1000);
            minutes = seconds / 60;
            hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            time.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.text_time);
        startPause = findViewById(R.id.button_start_or_pause);
        activity = findViewById(R.id.button_timer_or_stopwatch);
        reset = findViewById(R.id.button_reset);

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startPause.getText().equals("Pause"))
                {
                    timerHandler.removeCallbacks(timerRunnable);
                    startPause.setText("Start");
                }
                else
                {
                    startTime = System.currentTimeMillis() - milliseconds;
                    timerHandler.postDelayed(timerRunnable, 0);
                    startPause.setText("Pause");
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milliseconds = 0;
                timerHandler.removeCallbacks(timerRunnable);
                startPause.setText("Start");
                time.setText("00:00:00");
            }
        });

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset.performClick();
                Intent i = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(i);
            }
        });
    }
}