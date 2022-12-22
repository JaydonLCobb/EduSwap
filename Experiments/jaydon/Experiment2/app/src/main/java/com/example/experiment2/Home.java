package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Home extends AppCompatActivity {
    boolean isValid = false;
    private EditText userName;
    private EditText passWord;
    private Button login;
    private String User = "Username";
    private String Password = "123456";
    private int index = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userName = findViewById(R.id.UsernameTextField);
        passWord = findViewById(R.id.PasswordTextField);
        login = findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String inputUser = userName.getText().toString();
                String inputPass = passWord.getText().toString();
                isValid = validate(inputUser, inputPass);

                if (inputUser.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(Home.this, "User/Pass missing", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = validate(inputUser, inputPass);
                    if (!isValid) {
                        index = index - 1;
                        Toast.makeText(Home.this, "User/Pass incorrect", Toast.LENGTH_SHORT).show();
                        lockOut(index);
                    } else {
                        Intent intentWelcome = new Intent(Home.this, Welcome.class);
                        startActivity(intentWelcome);
                    }
                }
                }
    });
}

    private boolean validate (String u, String p) {
        if (u.equals(User) && p.equals(Password)) {
            return true;
        } else {
            return false;
        }
    }

            private void lockOut(int in) {
                if (in == 0) {
                    userName.setEnabled(false);
                    passWord.setEnabled(false);
                    passWord.setEnabled(false);
                    login.setEnabled(false);

                }
            }

}