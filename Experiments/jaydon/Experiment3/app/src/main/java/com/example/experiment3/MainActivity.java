package com.example.experiment3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    private Button utc;
    private Button cst;
    Thread t = null;
    private TextView dateTime;
    private boolean isRunning = false;
    private boolean isStopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // utc = findViewById(R.id.UTCButton);
        // cst = findViewById(R.id.CSTButton);

        Runnable run = new TimeRun();
        t = new Thread(run);
        t.start();
    }


    public void running() {
        runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                    Instant now = Instant.now();
                    TextView dateTime = findViewById(R.id.TimeTextView);
                    TextView local = findViewById(R.id.localTimeTextView);
                    LocalDateTime localTime = LocalDateTime.now();
                    Instant hours = now;
                    String presentTime = hours.toString();
                    dateTime.setText("The UTC time is: " + presentTime);
                    String localPresentTime = localTime.toString();
                    local.setText("The CST time is: " + localPresentTime);
            }
        });
    }

    class TimeRun implements Runnable{
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                    running();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch(Exception e){
                    //error
                }
            }
        }
    }
}