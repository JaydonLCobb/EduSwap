package com.example.experiment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    CountDownTimer timer;
    TextView timeText;
    SeekBar seekTimer;
    Button startPause;
    Button activity;
    Button reset;

    int time = 15;
    long timeMilli = time * 60000;
    int seconds;
    int minutes;
    int hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timeText = findViewById(R.id.text_time);
        seekTimer = findViewById(R.id.seekBar_time_select);
        startPause = findViewById(R.id.button_start_or_pause);
        activity = findViewById(R.id.button_timer_or_stopwatch);
        reset = findViewById(R.id.button_reset);

        seekTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                time = (i + 1) * 5;
                timeMilli = time * 60000;

                if (time == 60)
                {
                    timeText.setText("01:00:00");
                }
                else
                {
                    timeText.setText(String.format("00:%02d:00", time));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //do nothing
            }
        });

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startPause.getText().equals("Pause"))
                {
                    startPause.setText("Start");
                    timer.cancel();
                }
                else
                {
                    seekTimer.setEnabled(false);
                    startPause.setText("Pause");

                    timer = new CountDownTimer(timeMilli, 1000) {
                        @Override
                        public void onTick(long l) {
                            timeMilli = l;

                            seconds = (int) timeMilli / 1000;
                            minutes = seconds / 60;
                            hours = minutes / 60;
                            seconds = seconds % 60;
                            minutes = minutes % 60;

                            timeText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();
                }
            }
        });

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset.performClick();
                Intent i = new Intent(TimerActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPause.setText("Start");
                seekTimer.setEnabled(true);
                time = (seekTimer.getProgress() + 1) * 5;
                timeMilli = (long) time * 60000;
                timer.cancel();

                if (time == 60)
                {
                    timeText.setText("01:00:00");
                }
                else
                {
                    timeText.setText(String.format("00:%02d:00", time));
                }
            }
        });
    }
}