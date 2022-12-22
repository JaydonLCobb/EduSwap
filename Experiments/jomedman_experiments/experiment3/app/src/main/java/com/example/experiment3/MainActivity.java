package com.example.experiment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button button_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.RadioGroup);
        button_go = findViewById(R.id.button_go);

        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                if (radioId == R.id.radio_one)
                {
                    Intent i = new Intent(MainActivity.this, OneActivity.class);
                    startActivity(i);
                }
                else if (radioId == R.id.radio_two)
                {
                    Intent i = new Intent(MainActivity.this, TwoActivity.class);
                    startActivity(i);
                }
                else if (radioId == R.id.radio_three)
                {
                    Intent i = new Intent(MainActivity.this, ThreeActivity.class);
                    startActivity(i);
                }
                else if (radioId == R.id.radio_four)
                {
                    Intent i = new Intent(MainActivity.this, FourActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}